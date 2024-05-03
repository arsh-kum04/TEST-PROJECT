**Folder Name: fragments**
**File Name: AddNotesFragment.java**

**Line by line documented Code:**

```java
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
    private Dialog mProgressDialog; // This variable represents the progress dialog.

    /**
     * onCreateView method inflates the layout for this fragment and performs necessary initialization.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The previously saved state of this fragment.
     * @return Returns the root view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_notes, container, false);

        // Accessing title and description of notes
        TextView etNotesTitle = rootView.findViewById(R.id.etNotesTitle); // This variable accesses the title of the note.
        TextView etNotesDescription = rootView.findViewById(R.id.etNotesBody); // This variable accesses the description of the note.

        // close button to get back to previous fragment
        ImageButton closeBtn = rootView.findViewById(R.id.closeBtn); // This variable references the close button.
        closeBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method handles the click event on the close button.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                // Pop the fragment back stack to go to the previous fragment
                fragmentManager.popBackStack(); // This line pops the fragment back stack, which returns the user to the previous fragment.
            }
        });

        // save button to save the notes using FirestoreClass
        TextView tvSaveBtn = rootView.findViewById(R.id.tvSaveBtn); // This variable references the save button.
        tvSaveBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method handles the click event on the save button.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if (etNotesTitle.getText().isNullOrBlank()) {
                    Toast.makeText(context,
                        "Please enter the Notes Title",
                        Toast.LENGTH_LONG).show();
                } else {
                    // Show a progress dialog while saving the note
                    showProgressDialog(); // This line displays a progress dialog while the note is being saved.

                    // Get the current user's ID
                    String currentUserId = FirestoreClass.getCurrentUserId(); // This line retrieves the current user's ID.

                    // Create a new Note object with the user ID, title, and description
                    Note note = new Note(currentUserId,
                        etNotesTitle.getText().toString(),
                        etNotesDescription.getText().toString()); // This line creates a new Note object with the user ID, title, and description.

                    // Use FirestoreClass to create the note in the database
                    FirestoreClass.createNote(this, note); // This line uses the FirestoreClass to create the note in the database.
                }
            }
        });

        return rootView;
    }

    /**
     * showProgressDialog method creates and displays a progress dialog.
     */
    public void showProgressDialog() {
        // Initialize the progress dialog
        mProgressDialog = new Dialog(requireContext());
        // Set the layout of the progress dialog
        mProgressDialog.setContentView(R.layout.dialog_progress);
        // Show the progress dialog
        mProgressDialog.show();
    }

    /**
     * hideProgressDialog method dismisses the progress dialog.
     */
    public void hideProgressDialog() {
        // Dismiss the progress dialog
        mProgressDialog.dismiss();
    }

    /**
     * noteAddedSuccessfully method is called when the note has been successfully added to the database.
     */
    public void noteAddedSuccessfully() {
        // Hide the progress dialog
        hideProgressDialog();
        // Pop the fragment back stack to go to the previous fragment
        fragmentManager.popBackStack(); // This line pops the fragment back stack, which returns the user to the previous fragment.
    }
}
```