**Folder Name: Firebase**

**File Name: FirestoreClass.java**

```java
// Import necessary libraries.

// Class to handle Firebase operations.
public class FirestoreClass {

    // Initialize Firebase Firestore instance.
    private val mFireStore = FirebaseFirestore.getInstance();

    // Get current user ID.
    public String getCurrentUserId() {
        // Get current user from Firebase Authentication.
        var currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Initialize current user ID.
        var currentUserID = "";
        // Check if current user is not null.
        if (currentUser != null) {
            // Set current user ID to current user's UID.
            currentUserID = currentUser.getUid();
        }
        // Return current user ID.
        return currentUserID;
    }

    // Create a note in Firestore.
    public void createNote(fragment: AddNotesFragment, note: Note) {
        // Create a document in "notes" collection for the current user.
        mFireStore.collection(Constants.NOTES)
            .document()
            .set(note, SetOptions.merge())
            // Add success listener.
            .addOnSuccessListener({
                // Show toast message indicating successful note addition.
                Toast.makeText(fragment.requireContext(), "Note Added Successfully", Toast.LENGTH_LONG).show();
                // Notify fragment that note has been added successfully.
                fragment.noteAddedSuccessfully();
            })
            // Add failure listener.
            .addOnFailureListener({ excepction ->
                // Log error message.
                Log.e(fragment.javaClass.simpleName, "Error while adding note", excepction);
                // Show toast message indicating error adding note.
                Toast.makeText(fragment.requireContext(), "Error while adding note", Toast.LENGTH_LONG).show();
            });
    }

    // Get list of notes from Firestore.
    public void getNotesList(fragment: NotesFragment) {
        // Query "notes" collection for documents where "current_user_id" field matches current user ID and order by "timestamp" field in descending order.
        mFireStore.collection(Constants.NOTES)
            .whereEqualTo(Constants.CURRENT_USER_ID, getCurrentUserId())
            .orderBy(Constants.TIMESTAMP, Query.Direction.DESCENDING)
            .get()
            // Add success listener.
            .addOnSuccessListener({
                document ->
                // Initialize array list to store notes.
                val notesList: ArrayList<Note> = ArrayList();
                // Iterate over documents.
                for (i in document.documents) {
                    // Convert document to Note object.
                    val note = i.toObject(Note::class.java)!!;
                    // Set document ID to note.
                    note.documentId = i.id;
                    // Add note to notes list.
                    notesList.add(note);
                }
                // Pass notes list to fragment for UI setup.
                fragment.setupUI(notesList);
            })
            // Add failure listener.
            .addOnFailureListener({e->
                fragment.hideProgressDialog();
                // Log error message.
                Log.e(fragment.javaClass.simpleName , "Error while loading Notes" , e);
                // Log error message for debugging purposes.
                Log.d("TAG", "getNotesList: $e");
            });
    }


    // Delete a note from Firestore.
    public void deleteNote(fragment: NotesFragment, noteId: String) {
        // Delete document from "notes" collection with matching "noteId".
        mFireStore.collection(Constants.NOTES)
            .document(noteId)
            .delete()
            // Add success listener.
            .addOnSuccessListener({
                // Show toast message indicating successful note deletion.
                Toast.makeText(fragment.context, "Note Deleted", Toast.LENGTH_LONG).show();
                // Notify fragment that note has been deleted successfully.
                fragment.noteDeletedSuccessfully();
            })
            // Add failure listener.
            .addOnFailureListener({
                fragment.hideProgressDialog();
                // Show toast message indicating error deleting note.
                Toast.makeText(fragment.context, "Error while deleting the note", Toast.LENGTH_LONG).show();
            });
    }


}
```