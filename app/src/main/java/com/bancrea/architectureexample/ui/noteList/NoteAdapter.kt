package com.bancrea.architectureexample.ui.noteList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bancrea.architectureexample.R
import com.bancrea.architectureexample.data.db.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private var notes:List<Note> = ArrayList<Note>()
    var listener: OnInteractorListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        var currentNote: Note = notes.get(position)

        holder.descriptionTextView.text = currentNote.description
        holder.priorityTextView.text = currentNote.priority.toString()
        holder.titleTextView.text = currentNote.title
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION && listener != null)
                listener?.onItemClic(notes.get(position))
        }
    }

    fun setNotes(mNotes:List<Note>){
        this.notes= mNotes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note = notes.get(position)

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView:TextView = itemView.findViewById(R.id.title_label)
        var descriptionTextView:TextView = itemView.findViewById(R.id.description_label)
        var priorityTextView:TextView = itemView.findViewById(R.id.priority_label)
    }

    interface OnInteractorListener{
        fun onItemClic(note: Note)
    }
}