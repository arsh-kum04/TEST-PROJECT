**Folder Name:** com.example.notesapp_internshala.models
**File Name:** Note
**Code:**
```java
package com.example.notesapp_internshala.models;

import android.os.Parcel;
import android.os.Parcelable;

// Data class that represents an individual note
data class Note(
    // The ID of the current user as a string
    val currentUserId: String = "",
    // The title of the note as a string
    val title: String = "",
    // The description of the note as a string
    val description: String = "",
    // The document ID of the note in the database as a string
    var documentId: String = "",
    // The timestamp of the note's creation as a long integer
    val timestamp: Long = System.currentTimeMillis()
): Parcelable {

    // Constructor that initializes the Note object from a Parcel object
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    // Describe the contents of the Note object for parceling
    override fun describeContents(): Int {
        return 0
    }

    // Write the Note object to a Parcel object
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(documentId)
    }

    // Companion object that creates a Parcelable.Creator instance for the Note class
    companion object CREATOR : Parcelable.Creator<Note> {

        // Create a new Note object from a Parcel object
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        // Create a new Note object array of the specified size
        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
```