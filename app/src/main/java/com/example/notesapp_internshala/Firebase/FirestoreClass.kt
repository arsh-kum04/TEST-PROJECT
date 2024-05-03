**Folder Name:** Firebase
**File Name:** FirestoreClass.kt
**Line by Line Documented Code:**
```kotlin
// This class provides an interface to Firebase Firestore.
class FirestoreClass {

    // Access the Firestore instance.
    private val mFireStore = FirebaseFirestore.getInstance()

    // Get the current user's ID.
    fun getCurrentUserId(): String {
        // Get the current user from Firebase Authentication.
        var currentUser = FirebaseAuth.getInstance().currentUser
        
        // If the current user is not null, return the user ID.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        
        // Return the current user ID.
        return currentUserID
    }

    // Create a new note in the database.
    fun createNote(fragment: AddNotesFragment, note: Note) {
        // Add the note to the 'notes' collection.
        mFireStore.collection(Constants.NOTES)
            .document()
            .set(note, SetOptions.merge()) // Merge the new data with any existing data in the document
            .addOnSuccessListener {
                // Show a toast message to indicate that the note was added successfully.
                Toast.makeText(
                    fragment.requireContext(),
                    "Note Added Successfully",
                    Toast.LENGTH_LONG
                ).show()

                // Call the function to indicate that the note was added successfully.
                fragment.noteAddedSuccessfully()
            }
            .addOnFailureListener { execption ->

                // Log the error.
                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while adding note",
                    execption
                )

                // Show a toast message to indicate that there was an error adding the note.
                Toast.makeText(
                    fragment.requireContext(),
                    "Error while adding note",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    // Get a list of notes from the database.
    fun getNotesList(fragment: NotesFragment) {
        // Get the notes for the current user.
        mFireStore.collection(Constants.NOTES)
            .whereEqualTo(Constants.CURRENT_USER_ID, getCurrentUserId()) // Filter the collection by the current user's ID
            .orderBy(Constants.TIMESTAMP, Query.Direction.DESCENDING) // Order the results by timestamp in descending order
            .get()
            .addOnSuccessListener {
                // Create an array list to store the notes.
                val notesList: ArrayList<Note> = ArrayList()

                // Loop through the documents in the collection.
                for (i in document.documents) {
                    // Convert the document to a Note object.
                    val note = i.toObject(Note::class.java)!!

                    // Set the document ID for the note.
                    note.documentId = i.id

                    // Add the note to the array list.
                    notesList.add(note)
                }

                // Call the function to set up the UI with the notes.
                fragment.setupUI(notesList)
            }
            .addOnFailureListener {e->
                // Hide the progress dialog.
                fragment.hideProgressDialog()

                // Log the error.
                Log.e(fragment.javaClass.simpleName , "Error while loading Notes" , e)
            }
    }
// Delete a note from the database.
    fun deleteNote(fragment: NotesFragment, noteId: String) {
        // Delete the note from the 'notes' collection.
        mFireStore.collection(Constants.NOTES)
            .document(noteId)
            .delete()
            .addOnSuccessListener {
                // Show a toast message to indicate that the note was deleted successfully.
                Toast.makeText(fragment.context, "Note Deleted", Toast.LENGTH_LONG).show()

                // Call the function to indicate that the note was deleted successfully.
                fragment.noteDeletedSuccessfully()
            }
            .addOnFailureListener {
                // Hide the progress dialog.
                fragment.hideProgressDialog()

                // Show a toast message to indicate that there was an error deleting the note.
                Toast.makeText(fragment.context, "Error while deleting the note", Toast.LENGTH_LONG).show()
            }
    }
}
```