**Folder Name:** fragments
**File Name:** AddNotesFragment.kt
**Line by Line Documented Code**:

```java
// Importing required libraries
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.notesapp_internshala.Firebase.FirestoreClass;
import com.example.notesapp_internshala.R;
import com.example.notesapp_internshala.models.Note;

// Class Definition of AddNotesFragment
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
            fragmentManager?.popBackStack()
        }

        // save button to save the notes using Firestore Database
        val tvSaveBtn: TextView = rootView.findViewById(R.id.tvSaveBtn);
        tvSaveBtn.setOnClickListener {
            if (etNotesTitle.text.isNullOrBlank()) {
                Toast.makeText(context,
                    "Please enter the Notes Title",
                    Toast.LENGTH_LONG).show()
            } else {
                showProgressDialog()
                val currentUserId = FirestoreClass().getCurrentUserId()
                val note = Note(currentUserId,etNotesTitle.text.toString(), etNotesDescription.text.toString())
                FirestoreClass().createNote(this, note)
            }
        }
        return rootView
    }

    fun showProgressDialog() {
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.show();
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