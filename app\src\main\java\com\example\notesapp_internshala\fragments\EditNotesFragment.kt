### Folder Name: fragments

### File Name: EditNotesFragment.kt

#### Line by line documented Code:

```kotlin
// Package declaration
// Defines the package in which the code resides.
package com.example.notesapp_internshala.fragments

// Import necessary classes and libraries
// This line imports all the necessary classes and libraries used in the code.
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

// Class declaration
// This is the class declaration for the EditNotesFragment, which extends the Fragment class.
class EditNotesFragment : Fragment() {

    // Companion object
    // A companion object is used to declare constants for the class.
    companion object {
        const val ARG_NOTE = "note"
    }

    // Member variables
    // These are the member variables of the class.
    private lateinit var mProgressDialog: Dialog
    private val mFireStore = FirebaseFirestore.getInstance()

    // onCreateView() method
    // This method is called when the fragment is created and returns the View associated with the fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        // This line inflates the layout for the fragment.
        val rootView =  inflater.inflate(R.layout.fragment_edit_notes, container, false)

        // Retrieve the note object passed from the previous fragment using arguments.getParcelable<>().
        // This line retrieves the note object passed from the previous fragment using arguments.getParcelable<>().
        val note = arguments?.getParcelable<Note>(ARG_NOTE)

        // Access the title and description of the note.
        // These lines access the title and description of the note.
        val etEditNotesTitle: TextView = rootView.findViewById(R.id.etEditNotesTitle)
        val etEditNotesDescription: TextView = rootView.findViewById(R.id.etEditNotesBody)

        // If note is not null, set the title and description to the corresponding EditText views.
        // These lines check if the note is not null and set the title and description to the corresponding EditText views.
        if (note != null) {
            etEditNotesTitle.text = note.title
            etEditNotesDescription.text = note.description
        }


        val tvEditBtn: TextView = rootView.findViewById(R.id.tvEditBtn)
        tvEditBtn.setOnClickListener {
            // Check if the note title is empty or blank.
            // This line checks if the note title is empty or blank.
            if (etEditNotesTitle.text.isNullOrBlank()) {
                Toast.makeText(context, "Please enter the Notes Title", Toast.LENGTH_LONG).show()
            } else {

                // Show a progress dialog while updating the note.
                // This line shows a progress dialog while updating the note.
                showProgressDialog()

                // Get the document ID of the note to be updated.
                // This line gets the document ID of the note to be updated.
                Log.d("beforeDocumentId", "documentId: ${note!!.documentId}")

                // Create a HashMap to store the updated title and description of the note.
                // This line creates a HashMap to store the updated title and description of the note.
                val updateHashMap = HashMap<String, Any>()
                updateHashMap["title"] = etEditNotesTitle.text.toString()
                updateHashMap["description"] = etEditNotesDescription.text.toString()

                // Update the note in the Firestore database.
                // This line updates the note in the Firestore database.
                mFireStore.collection(Constants.NOTES)
                    .document(note.documentId)
                    .update(updateHashMap)
                    .addOnSuccessListener {

                        // Display a success message to the user.
                        // This line displays a success message to the user.
                        Toast.makeText(context, "Note updated successfully", Toast.LENGTH_LONG).show()

                        // Call the updateNoteSuccessfully() method to update the UI.
                        // This line calls the updateNoteSuccessfully() method to update the UI.
                        updateNoteSuccessfully()
                    }

                    .addOnFailureListener { exception ->
                        // Log the exception.
                        // This line logs the exception.
                        Log.d("TAG", "updateNote: $exception")

                        // Hide the progress dialog.
                        // This line hides the progress dialog.
                        hideProgressDialog()

                        // Display an error message to the user.
                        // This line displays an error message to the user.
                        Toast.makeText(context, "Error while updating the note", Toast.LENGTH_LONG).show()
                    }

            }
        }

        val closeBtn: ImageButton = rootView.findViewById(R.id.editCloseBtn)
        closeBtn.setOnClickListener {
            // Close the fragment and go back to the previous fragment.
            // This line closes the fragment and goes back to the previous fragment.
            fragmentManager?.popBackStack()
        }

        // Return the inflated view.
        // This line returns the inflated view.
        return rootView
    }

    // Method to show the progress dialog.
    // This method shows the progress dialog.
    fun showProgressDialog() {
        // Create a new dialog and set its content view to the dialog_progress layout.
        // This line creates a new dialog and sets its content view to the dialog_progress layout.
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // Show the progress dialog.
        // This line shows the progress dialog.
        mProgressDialog.show()
    }

    // Method to hide the progress dialog.
    // This method hides the progress dialog.
    fun hideProgressDialog() {
        // Hide the progress dialog.
        // This line hides the progress dialog.
        mProgressDialog.dismiss()
    }

    // Method to update the UI after successfully updating the note.
    // This method updates the UI after successfully updating the note.
    fun updateNoteSuccessfully() {

        // Hide the progress dialog.
        // This line hides the progress dialog.
        hideProgressDialog()

        // Log a message to indicate successful updation.
        // This line logs a message to indicate successful updation.
        Log.d("SUCCESS", "updateNoteSuccessfully")

        // Close the fragment and go back to the previous fragment.
        // This line closes the fragment and goes back to the previous fragment.
        fragmentManager?.popBackStack()
    }
}
```