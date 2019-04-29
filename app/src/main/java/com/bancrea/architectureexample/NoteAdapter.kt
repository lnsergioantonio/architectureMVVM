package com.bancrea.architectureexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private var notes:List<Note> = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        var currentNote:Note = notes.get(position)

        holder.descriptionTextView.text = currentNote.description
        holder.priorityTextView.text = currentNote.priority.toString()
        holder.titleTextView.text = currentNote.title
    }

    fun setNotes(mNotes:List<Note>){
        this.notes= mNotes
        notifyDataSetChanged()
    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView:TextView = itemView.findViewById(R.id.title_label)
        var descriptionTextView:TextView = itemView.findViewById(R.id.description_label)
        var priorityTextView:TextView = itemView.findViewById(R.id.priority_label)
    }
}