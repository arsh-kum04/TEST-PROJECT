```
// Folder Name: fragments
// File Name: AddNotesFragment.kt

// Importing required libraries
import android.app.Dialog
import android.os.Bundle
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

// Class Definition of AddNotesFragment
class AddNotesFragment : Fragment() {
    // Variable for progress dialog
    lateinit var mProgressDialog: Dialog

    // onCreateView method of Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_notes, container, false)

        // Accessing title and description of notes
        val etNotesTitle: TextView = rootView.findViewById(R.id.etNotesTitle)
        val etNotesDescription: TextView = rootView.findViewById(R.id.etNotesBody)

        // close button to get back to previous fragment
        val closeBtn: ImageButton = rootView.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener {
            fragmentManager?.popBackStack()
        }



        // save button to save the notes using Room Database
        val tvSaveBtn: TextView = rootView.findViewById(R.id.tvSaveBtn)
        tvSaveBtn.setOnClickListener{
            if(etNotesTitle.text.isNullOrBlank()) {
                Toast.makeText(context,
                    "Please enter the Notes Title",
                    Toast.LENGTH_LONG).show()
            } else {
                showProgressDialog()
               // addNoteToDatabase(notesDao, etNotesTitle.text.toString(), etNotesDescription.text.toString())

                val currentUserId = FirestoreClass().getCurrentUserId()

                val note = Note(currentUserId,
                    etNotesTitle.text.toString(),
                    etNotesDescription.text.toString())

                FirestoreClass().createNote(this, note)
            }
        }

        return rootView
    }


    fun showProgressDialog() {

        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun noteAddedSuccessfully() {
        hideProgressDialog()
        fragmentManager?.popBackStack()
    }

}
```
1. **Folder Name**: fragments
2. **File Name**: AddNotesFragment.kt
3. **Line by Line Documented Code**:
   - **Line 1**: Importing required libraries for Android development and working with fragments.
   - **Line 7-9**: Class definition for AddNotesFragment, a subclass of Fragment, for adding new notes.
   - **Line 11**: Declaring a variable for progress dialog.
   - **Line 13-26**: onCreateView method, responsible for fragment lifecycle and initializing the user interface.
   - **Line 15**: Inflating layout for this fragment, i.e., fragment_add_notes.xml.
   - **Line 17-18**: Accessing views from the inflated layout, including title and description edit texts.
   - **Line 20-22**: Setting close button's click listener to navigate back to the previous fragment.
   - **Line 24-37**: Setting save button's click listener to save the note using Firestore database if a title is provided.
   - **Line 25**: Checking if the title is empty and displaying a toast message if so.
   - **Line 26**: Showing progress dialog when saving the note.
   - **Line 27**: Commenting out the previous code for adding notes to Room database.
   - **Line 28-30**: Getting the current user's ID from FirestoreClass.
   - **Line 31-33**: Creating a Note object with title, description, and current user ID.
   - **Line 34**: Calling createNote method from FirestoreClass to save the note.
   - **Line 39-41**: showProgressDialog method to display a progress dialog.
   - **Line 43-45**: hideProgressDialog method to hide the progress dialog.
   - **Line 47-49**: noteAddedSuccessfully method to handle successful note addition, hide progress dialog, and navigate back.