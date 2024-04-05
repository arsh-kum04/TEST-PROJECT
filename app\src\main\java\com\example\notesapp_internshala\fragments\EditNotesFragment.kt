### Folder Name: fragments

### File Name: EditNotesFragment.kt

### Line by line documented Code:

```kotlin
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


class EditNotesFragment : Fragment() {

    companion object {
        const val ARG_NOTE = "note"
    }

    // lateinit is used to initialize a variable later in the code.
    // mProgressDialog: Dialog used to show a progress dialog while updating the note.
    private lateinit var mProgressDialog: Dialog

    // mFireStore: FirebaseFirestore represents an object of the Firestore database.
    private val mFireStore = FirebaseFirestore.getInstance()

    // onCreateView() method is called when the fragment is created and returns the View associated with the fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment.
        val rootView =  inflater.inflate(R.layout.fragment_edit_notes, container, false)

        // Retrieving the note object passed from the previous fragment using arguments.getParcelable<>().
        val note = arguments?.getParcelable<Note>(ARG_NOTE)

        // Accessing the title and description of the note.
        val etEditNotesTitle: TextView = rootView.findViewById(R.id.etEditNotesTitle)
        val etEditNotesDescription: TextView = rootView.findViewById(R.id.etEditNotesBody)

        // If note is not null, set the title and description to the corresponding EditText views.
        if (note != null) {
            etEditNotesTitle.text = note.title
            etEditNotesDescription.text = note.description
        }


        val tvEditBtn: TextView = rootView.findViewById(R.id.tvEditBtn)
        tvEditBtn.setOnClickListener{

            // Checking if the note title is empty or blank.
            if(etEditNotesTitle.text.isNullOrBlank()) {
                Toast.makeText(context,
                    "Please enter the Notes Title",
                    Toast.LENGTH_LONG).show()
            } else {

                // Showing a progress dialog while updating the note.
                showProgressDialog()
                
                // Getting the document ID of the note to be updated.
                Log.d("beforeDocumentId", "documentId: ${note!!.documentId}")

                // Creating a HashMap to store the updated title and description of the note.
                val updateHashMap = HashMap<String , Any>()
                updateHashMap["title"] = etEditNotesTitle.text.toString()
                updateHashMap["description"] = etEditNotesDescription.text.toString()


                // Updating the note in the Firestore database.
                mFireStore.collection(Constants.NOTES)
                    .document(note.documentId)
                    .update(updateHashMap)
                    .addOnSuccessListener {

                        // Display a success message to the user.
                        Toast.makeText(context, "Note updated successfully", Toast.LENGTH_LONG).show()

                        // Calling the updateNoteSuccessfully() method to update the UI.
                        updateNoteSuccessfully()
                    }

                    .addOnFailureListener {exception->

                        // Log the exception.
                        Log.d("TAG", "updateNote: $exception")

                        // Hide the progress dialog.
                        hideProgressDialog()

                        // Display an error message to the user.
                        Toast.makeText(context, "Error while updating the note", Toast.LENGTH_LONG).show()
                    }

            }
        }

        val closeBtn: ImageButton = rootView.findViewById(R.id.editCloseBtn)
        closeBtn.setOnClickListener {
            // Close the fragment and go back to the previous fragment.
            fragmentManager?.popBackStack()
        }

        // Return the inflated view.
        return rootView
    }


    // Method to show the progress dialog.
    fun showProgressDialog() {

        // Create a new dialog and set its content view to the dialog_progress layout.
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // Show the progress dialog.
        mProgressDialog.show()
    }

    // Method to hide the progress dialog.
    fun hideProgressDialog() {
        // Hide the progress dialog.
        mProgressDialog.dismiss()
    }

    // Method to update the UI after successfully updating the note.
    fun updateNoteSuccessfully() {

        // Hide the progress dialog.
        hideProgressDialog()

        // Log a message to indicate successful updation.
        Log.d("SUCCESS", "updateNoteSuccessfully")

        // Close the fragment and go back to the previous fragment.
        fragmentManager?.popBackStack()
    }



}
```