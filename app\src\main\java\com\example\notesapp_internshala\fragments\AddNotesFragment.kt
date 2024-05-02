```java
// Folder Name: fragments
// File Name: AddNotesFragment.java

// This class represents a fragment for adding new notes to the application.

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

public class AddNotesFragment extends Fragment {
    private Dialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_notes, container, false);

        // Accessing title and description of notes
        TextView etNotesTitle = rootView.findViewById(R.id.etNotesTitle);
        TextView etNotesDescription = rootView.findViewById(R.id.etNotesBody);

        // close button to get back to previous fragment
        ImageButton closeBtn = rootView.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop the fragment back stack to go to the previous fragment
                fragmentManager.popBackStack();
            }
        });

        // save button to save the notes using FirestoreClass
        TextView tvSaveBtn = rootView.findViewById(R.id.tvSaveBtn);
        tvSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNotesTitle.getText().isNullOrBlank()) {
                    Toast.makeText(context,
                        "Please enter the Notes Title",
                        Toast.LENGTH_LONG).show();
                } else {
                    // Show a progress dialog while saving the note
                    showProgressDialog();

                    // Get the current user's ID
                    String currentUserId = FirestoreClass.getCurrentUserId();

                    // Create a new Note object with the user ID, title, and description
                    Note note = new Note(currentUserId,
                        etNotesTitle.getText().toString(),
                        etNotesDescription.getText().toString());

                    // Use FirestoreClass to create the note in the database
                    FirestoreClass.createNote(this, note);
                }
            }
        });

        return rootView;
    }

    public void showProgressDialog() {
        // Initialize the progress dialog
        mProgressDialog = new Dialog(requireContext());
        // Set the layout of the progress dialog
        mProgressDialog.setContentView(R.layout.dialog_progress);
        // Show the progress dialog
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        // Dismiss the progress dialog
        mProgressDialog.dismiss();
    }

    public void noteAddedSuccessfully() {
        // Hide the progress dialog
        hideProgressDialog();
        // Pop the fragment back stack to go to the previous fragment
        fragmentManager.popBackStack();
    }
}
```