package com.bancrea.architectureexample

import android.app.Activity
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val ADD_NOTE_REQUEST = 1
    private lateinit var noteViewModel:NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = NoteAdapter()
        val recycler : RecyclerView = findViewById(R.id.recycler)
        recycler.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel(application)::class.java)

        noteViewModel.allNotes.observe(this, Observer { notes ->
            adapter.setNotes(notes)
        })

        val fab:FloatingActionButton = findViewById(R.id.floatingActionButton)

        fab.setOnClickListener {
            startActivity(Intent(this,AddNoteActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==ADD_NOTE_REQUEST && resultCode== Activity.RESULT_OK){
            if(data!=null){
                val note = Note()
                note.title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE)
                note.description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
                note.priority = data.getIntExtra(AddNoteActivity.EXTRA_NUMBER,0)

                noteViewModel.insert(note)
            }
        }else{
            Toast.makeText(this,"Note not saved",Toast.LENGTH_LONG).show()
        }
    }
}
