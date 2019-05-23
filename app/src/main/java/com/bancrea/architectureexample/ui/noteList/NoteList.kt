package com.bancrea.architectureexample.ui.noteList

import android.app.Activity
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bancrea.architectureexample.R
import com.bancrea.architectureexample.data.db.Note
import com.bancrea.architectureexample.ui.formNote.FormNote
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteList : AppCompatActivity() {
    companion object{
        private val ADD_NOTE_REQUEST = 1
        private val UPDATE_NOTE_REQUEST = 2
    }
    private lateinit var noteViewModel: NoteViewModel
    private val adapter = NoteAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler : RecyclerView = findViewById(R.id.recycler)
            recycler.adapter = adapter

        adapter.listener = object: NoteAdapter.OnInteractorListener {
            override fun onItemClic(note: Note) {
                val intent = Intent(applicationContext, FormNote::class.java)
                intent.putExtra(FormNote.EXTRA_ID,note.id)
                intent.putExtra(FormNote.EXTRA_DESCRIPTION,note.description)
                intent.putExtra(FormNote.EXTRA_NUMBER,note.priority)
                intent.putExtra(FormNote.EXTRA_TITLE,note.title)

                startActivityForResult(intent, UPDATE_NOTE_REQUEST)
            }
        }

        noteViewModel = ViewModelProviders.of(this).get(
            NoteViewModel(
                application
            )::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            adapter.setNotes(notes)
        })

        val fab:FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, FormNote::class.java)
            startActivityForResult(intent,
                ADD_NOTE_REQUEST
            )
        }
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(applicationContext,"Note deleted",Toast.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recycler)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== ADD_NOTE_REQUEST && resultCode== Activity.RESULT_OK){
            if(data!=null){
                val note = Note()
                note.title = data.getStringExtra(FormNote.EXTRA_TITLE)
                note.description = data.getStringExtra(FormNote.EXTRA_DESCRIPTION)
                note.priority = data.getIntExtra(FormNote.EXTRA_NUMBER,0)

                noteViewModel.insert(note)
            }
        }else if (requestCode== UPDATE_NOTE_REQUEST && resultCode== Activity.RESULT_OK){
            if(data!=null){
                val id = data.getIntExtra(FormNote.EXTRA_ID,-1)
                if(id!=-1){
                    val note = Note(
                        id,
                        data.getStringExtra(FormNote.EXTRA_TITLE),
                        data.getStringExtra(FormNote.EXTRA_DESCRIPTION),
                        data.getIntExtra(FormNote.EXTRA_NUMBER, 0)
                    )
                    noteViewModel.update(note)
                    Toast.makeText(this,"Note updated",Toast.LENGTH_LONG).show()
                }
            }else
                Toast.makeText(this,"Note can´t be updated",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"Note not saved",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.delete_all ->{
                noteViewModel.deleteAll()
                Toast.makeText(this,"Delete all notes",Toast.LENGTH_LONG).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
