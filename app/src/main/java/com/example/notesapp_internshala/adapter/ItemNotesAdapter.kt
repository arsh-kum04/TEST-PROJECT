**Folder Name: adapter**

**File Name: ItemNotesAdapter.kt**

```python
// Import required Android libraries and data model
package com.example.notesapp_internshala.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp_internshala.databinding.ItemsNotesBinding
import com.example.notesapp_internshala.models.Note

// Adapter class to bind data to RecyclerView
class ItemNotesAdapter(private val list: ArrayList<Note>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Interface to handle item clicks
    interface OnNoteItemClickListener {
        fun onEditClick(position: Int,  note: Note)
        fun onDeleteClick(position: Int, noteId: String )
    }

    // Inner class to hold the view references
    class MyViewHolder(binding: ItemsNotesBinding): RecyclerView.ViewHolder(binding.root) {
        val tvNoteTitle = binding.notesTitle
        val tvNoteDescription = binding.notesBody
        val ibEditNote = binding.editNotes
        val ibDeleteNote = binding.ibDeleteList
    }

    // Create the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemsNotesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    // Return the size of the dataset
    override fun getItemCount(): Int {
        return list.size
    }

    private var listener: OnNoteItemClickListener? = null // Initialize listener to null

    // Binds data to the view holder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Get the current note
        val model = list[position]

        // If the holder is an instance of MyViewHolder
        if(holder is MyViewHolder) {
            // Set the title and description texts
            holder.tvNoteTitle.text = model.title
            holder.tvNoteDescription.text = model.description

            // Set up the delete button click listener
            holder.ibDeleteNote.setOnClickListener {
                // If the listener is set
                if(listener != null) {
                    // Call the onDeleteClick method of the listener
                    listener!!.onDeleteClick(position, model.documentId)
                }
            }

            // Set up the edit button click listener
            holder.ibEditNote.setOnClickListener {
                // If the listener is set
                if(listener != null) {
                    // Call the onEditClick method of the listener
                    listener!!.onEditClick(position,  list[position])
                }
            }
        }
    }

    // Sets the click listener
    fun setOnClickListener(listener: OnNoteItemClickListener) {
        this.listener = listener
    }
}
```