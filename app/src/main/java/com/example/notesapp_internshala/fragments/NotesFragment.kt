**Folder Name:** fragments
**File Name:** NotesFragment.kt
**Line by Line Documented Code:**

```kotlin
// Package declaration
package com.example.notesapp_internshala.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp_internshala.Firebase.FirestoreClass
import com.example.notesapp_internshala.R
import com.example.notesapp_internshala.adapter.ItemNotesAdapter
import com.example.notesapp_internshala.databinding.FragmentNotesBinding
import com.example.notesapp_internshala.models.Note
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

// Class representing the NotesFragment
class NotesFragment : Fragment() {

    // Binding to the fragment layout
    private var binding: FragmentNotesBinding? = null
    private val _binding get() = binding!!

    // Adapter for the recycler view
    private lateinit var adapter: ItemNotesAdapter

    // Google Sign In Client
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    // Firebase Auth instance
    private lateinit var mAuth: FirebaseAuth

    // Progress Dialog
    lateinit var mProgressDialog: Dialog

    // Override method for creating the fragment view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        val rootView = _binding.root

        // Find the "Add Note" button
        val addNotesBtn: CardView = rootView.findViewById(R.id.addNoteBtn)

        // Set click listener for the "Add Note" button
        addNotesBtn.setOnClickListener {
            // Navigate to the AddNotesFragment
            view?.findNavController()?.navigate(R.id.NotesFragmentToAddNotesFragment)
        }

        // Set click listener for the settings icon
        binding!!.settingsIcon.setOnClickListener {
            // Show a popup menu when the settings icon is clicked
            showPopup(it)
        }

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance()

        // Get the current user
        val user = mAuth.currentUser

        // Set the user's name in the header
        binding!!.userName.text = "Hi, ${user?.displayName}"

        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Initialize Google Sign In client
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        // Show progress dialog
        showProgressDialog()

        // Fetch notes from Firestore
        FirestoreClass().getNotesList(this)

        return rootView
    }



    // Override method for destroying the fragment view
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // Function to show a popup menu
    fun showPopup(v : View){
        // Create a popup menu
        val popup = PopupMenu(requireContext(), v)

        // Inflate the menu resource into the popup
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_sign_out, popup.menu)

        // Set click listener for the popup menu items
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.action_sign_out -> {
                    // Sign out the user and navigate to the LoginFragment
                    showProgressDialog()
                    signOutAndStartSignInActivity()
                }
            }
            true
        }

        // Show the popup menu
        popup.show()
    }

    // Function to sign out the user and start the SignInActivity
    private fun signOutAndStartSignInActivity() {
        // Sign out from Firebase Auth
        mAuth.signOut()

        // Sign out from Google Sign In
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            // Clear the login state
            clearLoginState()

            // Navigate to the LoginFragment
            view?.findNavController()?.navigate(R.id.NotesFragmentToLoginFragment)

            // Hide the progress dialog
            hideProgressDialog()
        }
    }

    // Function to clear the login state
    private fun clearLoginState() {
        // Access SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Clear the login state
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_user_logged_in", false)
        editor.apply()
    }

    // Function to show progress dialog
    fun showProgressDialog() {

        // Initialize the progress dialog
        mProgressDialog = Dialog(requireContext())

        // Set the content view of the progress dialog
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // Show the progress dialog
        mProgressDialog.show()
    }

    // Function to hide progress dialog
    fun hideProgressDialog() {
        // Dismiss the progress dialog
        mProgressDialog.dismiss()
    }

    // Function to set up the UI after fetching notes from Firestore
    fun setupUI(notesList: ArrayList<Note>) {
        // Hide the progress dialog
        hideProgressDialog()

        // Check if the notes list is empty
        if(notesList.size > 0) {
            // Make the recycler view visible
            binding?.rvVisibleNotes?.visibility = View.VISIBLE

            // Make the "No notes added" text view invisible
            binding?.tvNoNotesAdded?.visibility = View.GONE

            // Set the layout manager for the recycler view
            binding?.rvVisibleNotes?.layoutManager = LinearLayoutManager(context)

            // Set the fixed size for the recycler view
            binding?.rvVisibleNotes?.setHasFixedSize(true)

            // Set the adapter for the recycler view
            adapter = ItemNotesAdapter(notesList)
            binding?.rvVisibleNotes?.adapter = adapter

            // Set click listeners for the notes
            adapter.setOnClickListener(object :
                ItemNotesAdapter.OnNoteItemClickListener{
                override fun onEditClick(position: Int,  note: Note) {
                    // Create a bundle to pass the note object to the EditNotesFragment
                    val bundle = Bundle().apply {
                        putParcelable("note" , note)
                    }

                    // Create an instance of the EditNotesFragment
                    val editNoteFragment = EditNotesFragment()

                    // Set the arguments for the EditNotesFragment
                    editNoteFragment.arguments = bundle

                    // Begin a fragment transaction
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()

                    // Replace the current fragment with the EditNotesFragment
                    transaction.replace(R.id.nav_host_fragment, editNoteFragment)

                    // Add the fragment to the back stack
                    transaction.addToBackStack(null) // This allows navigating back to the NotesFragment

                    // Commit the fragment transaction
                    transaction.commit()
                }

                override fun onDeleteClick(position: Int, noteId: String) {
                    // Show progress dialog
                    showProgressDialog()

                    // Delete the note from Firestore
                    FirestoreClass().deleteNote(this@NotesFragment, noteId)

                    // Remove the note from the adapter
                    adapter.notifyItemRemoved(position)

                    // Fetch notes from Firestore again to update the UI
                    FirestoreClass().getNotesList(this@NotesFragment)
                }

            })

        } else {
            // Make the recycler view invisible
            binding?.rvVisibleNotes?.visibility = View.GONE

            // Make the "No notes added" text view visible
            binding?.tvNoNotesAdded?.visibility = View.VISIBLE
        }
    }

    // Function to handle successful deletion of a note
    fun noteDeletedSuccessfully() {
        // Hide the progress dialog
        hideProgressDialog()
    }

}
```