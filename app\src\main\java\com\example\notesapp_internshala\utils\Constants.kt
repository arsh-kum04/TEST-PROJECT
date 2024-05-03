**Folder Name:** utils

**File Name:** Constants.java

```java
// Package name
package com.example.notesapp_internshala.utils;

// Import necessary packages
import android.content.Context;
import android.util.Log;

// Define a class named Constants to store application-wide constants
public class Constants {

    // Define a constant variable NOTES to store the name of the collection that will hold the notes
    public static final String NOTES = "notes";

    // Line by line documentation:
    // This is a constant variable that stores the name of the collection that will hold the notes.

    // Define a constant variable CURRENT_USER_ID to store the ID of the current user
    public static final String CURRENT_USER_ID = "currentUserId";

    // Line by line documentation:
    // This is a constant variable that stores the ID of the current user.

    // Define a constant variable TIMESTAMP to store the timestamp of the last update
    public static final String TIMESTAMP = "timestamp";

    // Line by line documentation:
    // This is a constant variable that stores the timestamp of the last update.

    // Define a method to get the application context
    public static Context getContext() {
        // Return the application context
        return com.example.notesapp_internshala.NoteApplication.getContext();
    }

    // Line by line documentation:
    // This method returns the application context.

    // Define a method to log a message to the console
    public static void log(String message) {
        // Log the message to the console
        Log.d("Constants", message);
    }

    // Line by line documentation:
    // This method logs a message to the console.
}
```

---

**Folder Name:** utils

**File Name:** Converter.java

```java
// Package name
package com.example.notesapp_internshala.utils;

// Import necessary packages
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.example.notesapp_internshala.logic.Note;
import com.example.notesapp_internshala.logic.Notebook;

// Define a class named Converter to convert Firestore documents to objects and vice-versa
public class Converter {

    // Define a method to convert a DocumentSnapshot to a Note object
    public static Note documentToNote(DocumentSnapshot document) {
        // Create a new Note object
        Note note = new Note();

        // Set the id of the Note object
        note.setId(document.getId());

        // Get the title of the note
        note.setTitle(document.getString("title"));

        // Get the content of the note
        note.setContent(document.getString("content"));

        // Get the timestamp of the note
        note.setTimestamp(document.getTimestamp("timestamp"));

        // Get the notebook id of the note
        note.setNotebookId(document.getString("notebookId"));

        // Return the Note object
        return note;
    }

    // Line by line documentation:
    // This method converts a DocumentSnapshot to a Note object.

    // Define a method to convert a DocumentSnapshot to a Notebook object
    public static Notebook documentToNotebook(DocumentSnapshot document) {
        // Create a new Notebook object
        Notebook notebook = new Notebook();

        // Set the id of the Notebook object
        notebook.setId(document.getId());

        // Get the title of the notebook
        notebook.setTitle(document.getString("title"));

        // Get the timestamp of the notebook
        notebook.setTimestamp(document.getTimestamp("timestamp"));

        // Return the Notebook object
        return notebook;
    }

    // Line by line documentation:
    // This method converts a DocumentSnapshot to a Notebook object.

    // Define a method to convert a Note object to a HashMap
    public static HashMap<String, Object> noteToHashMap(Note note) {
        // Create a new HashMap
        HashMap<String, Object> hashMap = new HashMap<>();

        // Set the title of the note in the HashMap
        hashMap.put("title", note.getTitle());

        // Set the content of the note in the HashMap
        hashMap.put("content", note.getContent());

        // Set the timestamp of the note in the HashMap
        hashMap.put("timestamp", note.getTimestamp());

        // Set the notebook id of the note in the HashMap
        hashMap.put("notebookId", note.getNotebookId());

        // Return the HashMap
        return hashMap;
    }

    // Line by line documentation:
    // This method converts a Note object to a HashMap.

    // Define a method to convert a Notebook object to a HashMap
    public static HashMap<String, Object> notebookToHashMap(Notebook notebook) {
        // Create a new HashMap
        HashMap<String, Object> hashMap = new HashMap<>();

        // Set the title of the notebook in the HashMap
        hashMap.put("title", notebook.getTitle());

        // Set the timestamp of the notebook in the HashMap
        hashMap.put("timestamp", notebook.getTimestamp());

        // Return the HashMap
        return hashMap;
    }

    // Line by line documentation:
    // This method converts a Notebook object to a HashMap.
}
```