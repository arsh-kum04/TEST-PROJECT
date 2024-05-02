**Folder Name:** com.example.notesapp_internshala.fragments

**File Name:** EditNotesFragment.kt

```kotlin
// Importing necessary libraries and classes
package com.example.notesapp_internshala.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.notesapp_internshala.Firebase.FirestoreClass
import com.example.notesapp_internshala.R
import com.example.notesapp_internshala.models.Note
import com.example.notesapp_internshala.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

// Declaring a companion object with a constant ARG_NOTE
class EditNotesFragment : Fragment() {

    companion object {
        const val ARG_NOTE = "note"
    }

    // Initializing lateinit variables
    private lateinit var mProgressDialog: Dialog
    private val mFireStore = FirebaseFirestore.getInstance()

    // onCreateView method which inflates the layout and handles UI interactions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_edit_notes, container, false)

        // Getting the note object from the arguments bundle
        val note = arguments?.getParcelable<Note>(ARG_NOTE)

        // Accessing title and description of notes
        val etEditNotesTitle: TextView = rootView.findViewById(R.id.etEditNotesTitle)
        val etEditNotesDescription: TextView = rootView.findViewById(R.id.etEditNotesBody)

        // Setting the title and description of the note if it is not null
        if (note != null) {
            etEditNotesTitle.text = note.title
            etEditNotesDescription.text = note.description
        }

        // Setting onClickListener for the edit button
        val tvEditBtn: TextView = rootView.findViewById(R.id.tvEditBtn)
        tvEditBtn.setOnClickListener {
            // Checking if the title is empty or not
            if(etEditNotesTitle.text.isNullOrBlank()) {
                Toast.makeText(context,
                    "Please enter the Notes Title",
                    Toast.LENGTH_LONG).show()
            } else {

                // Showing the progress dialog
                showProgressDialog()
                Log.d("beforeDocumentId", "documentId: ${note!!.documentId}")

                // Creating a hashmap to store the updated title and description
                val updateHashMap = HashMap<String , Any>()
                updateHashMap["title"] = etEditNotesTitle.text.toString()
                updateHashMap["description"] = etEditNotesDescription.text.toString()

                // Updating the note in the Firestore database using the documentId
                mFireStore.collection(Constants.NOTES)
                    .document(note.documentId)
                    .update(updateHashMap)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Note updated successfully", Toast.LENGTH_LONG).show()
                        updateNoteSuccessfully()
                    }

                    .addOnFailureListener {exception->
                        Log.d("TAG", "updateNote: $exception")
                        hideProgressDialog()
                        Toast.makeText(context, "Error while updating the note", Toast.LENGTH_LONG).show()
                    }

            }
        }

        // Setting onClickListener for the close button
        val closeBtn: ImageButton = rootView.findViewById(R.id.editCloseBtn)
        closeBtn.setOnClickListener {
            // Popping the fragment from the backstack
            fragmentManager?.popBackStack()
        }

        return rootView
    }


    // Method to show the progress dialog
    fun showProgressDialog() {

        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.show()
    }

    // Method to hide the progress dialog
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    // Method to handle successful update of the note
    fun updateNoteSuccessfully() {
        hideProgressDialog()
        Log.d("SUCCESS", "updateNoteSuccessfully")
        fragmentManager?.popBackStack()
    }



}
```