```java
// Folder Name: utils
// File Name: Constants.java

// This file contains constant variables used throughout the application.
object Constants {

    // Constant representing the notes collection in the database.
    /*
     * This constant represents the name of the collection in the database where notes are stored.
     * It is used in various places in the code to access the notes collection.
     * For example, to add a new note to the database, we would use the following code:
     *
     * ```
     * val note = Note(title = "My Note", description = "This is my note.")
     * db.collection(Constants.NOTES).document().set(note)
     * ```
     */
    const val NOTES: String = "notes"

    // Constant representing the current user's ID in the database.
    /*
     * This constant represents the ID of the current user in the database.
     * It is used in various places in the code to access the user's data.
     * For example, to get the current user's notes from the database, we would use the following code:
     *
     * ```
     * val notes = db.collection(Constants.NOTES).whereEqualTo(Constants.CURRENT_USER_ID, currentUser.id).get()
     * ```
     */
    const val CURRENT_USER_ID: String = "currentUserId"

    // Constant representing the timestamp field in the database.
    /*
     * This constant represents the name of the field in the database that stores the timestamp of when a document was created or updated.
     * It is used in various places in the code to track the age of documents.
     * For example, to get the timestamp of a document, we would use the following code:
     *
     * ```
     * val timestamp = document.get(Constants.TIMESTAMP)
     * ```
     */
    const val TIMESTAMP: String = "timestamp"
}
```