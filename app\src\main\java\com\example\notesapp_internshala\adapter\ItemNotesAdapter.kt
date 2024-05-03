```python
# 1. Folder Name: adapter
# 2. File Name: ItemNotesAdapter.kt
# 3. Line by line documented Code
package com.example.notesapp_internshala.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp_internshala.databinding.ItemsNotesBinding
import com.example.notesapp_internshala.models.Note

# This class defines the adapter used to display list items of notes.
class ItemNotesAdapter(private val list: ArrayList<Note>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    # Variable to hold the listener for item clicks.
    private var listener: OnNoteItemClickListener? = null

    # This class defines the ViewHolder for an item in the adapter.
    class MyViewHolder(binding: ItemsNotesBinding): RecyclerView.ViewHolder(binding.root) {
        # Member variables to hold views for the title, description, edit button and delete button.
        val tvNoteTitle = binding.notesTitle
        val tvNoteDescription = binding.notesBody
        val ibEditNote = binding.editNotes
        val ibDeleteNote = binding.ibDeleteList
    }

    # This function is called when a new ViewHolder is created.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        # Create a ViewHolder by inflating the layout using the binding class.
        return MyViewHolder(ItemsNotesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    # This function returns the number of items in the list.
    override fun getItemCount(): Int {
        return list.size
    }

    # This function binds the data to the ViewHolder.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        # Get the current Note object.
        val model = list[position]
        # Check if the ViewHolder is of type MyViewHolder.
        if(holder is MyViewHolder) {
            # Set the title and description of the Note.
            holder.tvNoteTitle.text = model.title
            holder.tvNoteDescription.text = model.description

            # Set the click listener for the delete button.
            holder.ibDeleteNote.setOnClickListener {
                # Check if the listener is not null.
                if(listener != null) {
                    # Call the onDeleteClick method of the listener.
                    listener!!.onDeleteClick(position, model.documentId)
                }
            }

            # Set the click listener for the edit button.
            holder.ibEditNote.setOnClickListener {
                # Check if the listener is not null.
                if(listener != null) {
                    # Call the onEditClick method of the listener.
                    listener!!.onEditClick(position,  list[position])
                }
            }
        }
    }

    # This function sets the listener for item clicks.
    fun setOnClickListener(listener: OnNoteItemClickListener) {
        this.listener = listener
    }

    # Interface to define the click listeners for item clicks.
    interface OnNoteItemClickListener {
        # Function to handle the edit button click.
        fun onEditClick(position: Int,  note: Note)
        # Function to handle the delete button click.
        fun onDeleteClick(position: Int, noteId: String )
    }
}
```