**Folder Name:** com.example.notesapp_internshala.models

**File Name:** Note.kt

```kotlin
// Data class to represent a Note object in the app
data class Note(

    // Unique identifier for the current user
    val currentUserId: String = "",

    // Title of the note
    val title: String = "",

    // Description of the note
    val description: String = "",

    // Unique identifier for the note in the database
    var documentId: String = "",

    // Timestamp of when the note was created or last updated
    val timestamp: Long = System.currentTimeMillis()
): Parcelable {

    // Secondary constructor for reading a Note object from a Parcel
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

    // Write the Note object to a Parcel
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(documentId)
    }

    // Parcelable Creator for creating Note objects from a Parcel
    companion object CREATOR : Parcelable.Creator<Note> {

        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
```