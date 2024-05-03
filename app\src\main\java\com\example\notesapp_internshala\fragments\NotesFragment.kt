**Folder Name:** com.example.notesapp_internshala.fragments

**File Name:** NotesFragment.kt

**Line by Line Documented Code:**

```kotlin
// Import necessary libraries
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

// Class for NotesFragment
class NotesFragment : Fragment() {

    // Declare variables and initialize them later
    private var binding: FragmentNotesBinding? = null
    private val _binding get() = binding!! // Safe check for null
    private lateinit var adapter: ItemNotesAdapter
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    lateinit var mProgressDialog: Dialog // Late initialized progress dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        val rootView = _binding.root

        // Find the "Add Note" button
        val addNotesBtn: CardView = rootView.findViewById(R.id.addNoteBtn)

        // Set onClick listener for the "Add Note" button
        addNotesBtn.setOnClickListener {
            // Navigate to the "Add Notes" fragment
            view?.findNavController()?.navigate(R.id.NotesFragmentToAddNotesFragment)
        }

        // Find the settings icon
        binding!!.settingsIcon.setOnClickListener {
            // Show the popup menu
            showPopup(it)
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Get current user
        val user = mAuth.currentUser

        // Set the user's name in the UI
        binding!!.userName.text = "Hi, ${user?.displayName}"

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // Show progress dialog
        showProgressDialog()

        // Get notes list from Firestore
        FirestoreClass().getNotesList(this) // Pass 'this' as an argument to access methods in this fragment

        // Return the root view
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // Function to show the popup menu
    fun showPopup(v : View){
        // Create a popup menu
        val popup = PopupMenu(requireContext(), v)

        // Inflate the menu resource
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_sign_out, popup.menu)

        // Set onMenuItemClickListener for the popup menu
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.action_sign_out -> {
                    // Show progress dialog
                    showProgressDialog()

                    // Sign out and navigate to the login fragment
                    signOutAndStartSignInActivity()
                }
            }
            // Return true to indicate that the menu item has been handled
            true
        }

        // Show the popup menu
        popup.show()
    }

    // Function to sign out and navigate to the login fragment
    private fun signOutAndStartSignInActivity() {
        // Sign out from Firebase Auth
        mAuth.signOut()

        // Sign out from Google Sign In
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            // Clear login state
            clearLoginState()

            // Navigate to the login fragment
            view?.findNavController()?.navigate(R.id.NotesFragmentToLoginFragment)

            // Hide progress dialog
            hideProgressDialog()
        }
    }

    // Function to clear login state
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

        // Set the content view
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // Show the progress dialog
        mProgressDialog.show()
    }

    // Function to hide progress dialog
    fun hideProgressDialog() {
        // Hide the progress dialog
        mProgressDialog.dismiss()
    }

    // Function to setup the UI with the notes list
    fun setupUI(notesList: ArrayList<Note>) {
        // Hide progress dialog
        hideProgressDialog()

        // If there are notes, show them in the recycler view
        if(notesList.size > 0) {
            binding!!.rvVisibleNotes.visibility = View.VISIBLE
            binding!!.tvNoNotesAdded.visibility = View.GONE
            binding!!.rvVisibleNotes.layoutManager = LinearLayoutManager(context)
            binding!!.rvVisibleNotes.setHasFixedSize(true)

            // Initialize the adapter
            adapter = ItemNotesAdapter(notesList)

            // Set the adapter to the recycler view
            binding!!.rvVisibleNotes.adapter = adapter

            // Set onClick listener for the adapter
            adapter.setOnClickListener(object :
                ItemNotesAdapter.OnNoteItemClickListener{
                // Edit note on click
                override fun onEditClick(position: Int,  note: Note) {
                    // Create a bundle to pass the note to the edit notes fragment
                    val bundle = Bundle().apply {
                        putParcelable("note" , note)
                    }

                    // Create an instance of the edit notes fragment
                    val editNoteFragment = EditNotesFragment()

                    // Set the arguments for the edit notes fragment
                    editNoteFragment.arguments = bundle

                    // Begin the transaction to replace the current fragment with the edit notes fragment
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()

                    // Replace the current fragment with the edit notes fragment
                    transaction.replace(R.id.nav_host_fragment, editNoteFragment)

                    // Add the transaction to the back stack
                    transaction.addToBackStack(null) // This allows navigating back to the NotesFragment

                    // Commit the transaction
                    transaction.commit()
                }

                // Delete note on click
                override fun onDeleteClick(position: Int, noteId: String) {
                    // Show progress dialog
                    showProgressDialog()

                    // Delete the note from Firestore
                    FirestoreClass().deleteNote(this@NotesFragment, noteId) // Pass 'this' as an argument to access methods in this fragment

                    // Remove the note from the adapter
                    adapter.notifyItemRemoved(position)

                    // Get the updated notes list from Firestore
                    FirestoreClass().getNotesList(this@NotesFragment) // Pass 'this' as an argument to access methods in this fragment
                }

            })
        } else {
            // If there are no notes, show a message
            binding!!.rvVisibleNotes.visibility = View.GONE
            binding!!.tvNoNotesAdded.visibility = View.VISIBLE
        }
    }

    // Function to indicate that the note was deleted successfully
    fun noteDeletedSuccessfully() {
        // Hide progress dialog
        hideProgressDialog()
    }

}
```