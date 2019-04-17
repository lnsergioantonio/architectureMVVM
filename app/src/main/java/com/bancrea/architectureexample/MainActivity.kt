package com.bancrea.architectureexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val noteViewModel by lazy {
            ViewModelProviders.of(this).get(NoteViewModel::class.java)
        }

        noteViewModel.allNotes.observe(this, Observer { note ->

        })
    }
}
