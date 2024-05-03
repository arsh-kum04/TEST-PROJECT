**Folder Name:** fragments
**File Name:** AddNotesFragment.kt
**Line by Line Documented Code**:
```java
// Importing required libraries
// Importing Dialog from Android library
import android.app.Dialog;
// Importing Bundle class from Android library
import android.os.Bundle;
// Importing Fragment from AndroidX library
import androidx.fragment.app.Fragment;
// Importing required libraries for manipulating view
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// Importing required libraries for finding Ids
import android.widget.ImageButton;
import android.widget.TextView;
// Importing required libraries to show toast
import android.widget.Toast;
// Importing FirebaseFirestoreClass from Firestore package
import com.example.notesapp_internshala.Firebase.FirestoreClass;
// Importing required resources from R package
import com.example.notesapp_internshala.R;
// Importing Note model
import com.example.notesapp_internshala.models.Note;

// Class Definition of AddNotesFragment extending Fragment
class AddNotesFragment : Fragment() {
    // Variable for progress dialog
    lateinit var mProgressDialog: Dialog;

    // onCreateView method of Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_notes, container, false);

        // Accessing title and description of notes
        val etNotesTitle: TextView = rootView.findViewById(R.id.etNotesTitle);
        val etNotesDescription: TextView = rootView.findViewById(R.id.etNotesBody);

        // close button to get back to previous fragment
        val closeBtn: ImageButton = rootView.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener{
            // Pop the fragment backstack to go back to previous fragment
            fragmentManager?.popBackStack()
        }

        // save button to save the notes using Firestore Database
        val tvSaveBtn: TextView = rootView.findViewById(R.id.tvSaveBtn);
        tvSaveBtn.setOnClickListener {
            if (etNotesTitle.text.isNullOrBlank()) {
                // Show a Toast message if the title is empty
                Toast.makeText(context,
                    "Please enter the Notes Title",
                    Toast.LENGTH_LONG).show()
            } else {
                // Show the progress dialog while saving the note
                showProgressDialog()
                // Get the current user ID
                val currentUserId = FirestoreClass().getCurrentUserId()
                // Create a Note object with the title, description, and current user ID
                val note = Note(currentUserId,etNotesTitle.text.toString(), etNotesDescription.text.toString())
                // Call the createNote function of FirestoreClass to save the note to the database
                FirestoreClass().createNote(this, note)
            }
        }
        return rootView
    }

    fun showProgressDialog() {
        // Initialize the progress dialog
        mProgressDialog = Dialog(requireContext())
        // Set the content view of the progress dialog
        mProgressDialog.setContentView(R.layout.dialog_progress);
        // Show the progress dialog
        mProgressDialog.show();
    }

    fun hideProgressDialog() {
        // Dismiss the progress dialog
        mProgressDialog.dismiss()
    }

    fun noteAddedSuccessfully() {
        // Hide the progress dialog
        hideProgressDialog()
        // Pop the fragment backstack to go back to the previous fragment
        fragmentManager?.popBackStack()
    }
}
```