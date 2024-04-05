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

    // Define a constant variable CURRENT_USER_ID to store the ID of the current user
    public static final String CURRENT_USER_ID = "currentUserId";

    // Define a constant variable TIMESTAMP to store the timestamp of the last update
    public static final String TIMESTAMP = "timestamp";

    // Define a method to get the application context
    public static Context getContext() {
        // Return the application context
        return com.example.notesapp_internshala.NoteApplication.getContext();
    }

    // Define a method to log a message to the console
    public static void log(String message) {
        // Log the message to the console
        Log.d("Constants", message);
    }
}
```