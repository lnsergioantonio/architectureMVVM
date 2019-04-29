package com.bancrea.architectureexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = NoteAdapter()
        val recycler : RecyclerView = findViewById(R.id.recycler)
        recycler.adapter = adapter

        val noteViewModel = ViewModelProviders.of(this).get(NoteViewModel(application)::class.java)

        noteViewModel.allNotes.observe(this, Observer { notes ->
            adapter.setNotes(notes)
        })



    }
}
