package com.bancrea.architectureexample.ui.noteList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bancrea.architectureexample.data.db.Note
import com.bancrea.architectureexample.data.db.NoteDatabase
import com.bancrea.architectureexample.data.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository
    val allNotes:LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getNoteDatabase(application).noteDao()
        repository= NoteRepository(noteDao)
        allNotes = repository.allNotes
    }

    fun insert(note: Note) = repository.insert(note)

    fun update(note: Note) = repository.update(note)

    fun delete(note: Note) = repository.delete(note)

    fun deleteAll() = repository.deleteAll()
}