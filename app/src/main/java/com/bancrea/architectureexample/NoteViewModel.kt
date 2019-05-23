package com.bancrea.architectureexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel (application: Application) : AndroidViewModel(application) {
    private var parentjob=Job()
    private val coroutineContext : CoroutineContext get() = parentjob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private var repository:NoteRepository
    val allNotes:LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getNoteDatabase(application).noteDao()
        repository= NoteRepository(noteDao)
        allNotes = repository.allNotes
    }

    fun insert(note:Note) = scope.launch(Dispatchers.IO){
        repository.insert(note)
    }

    fun update(note:Note){
        repository.update(note)
    }

    fun delete(note:Note){
        repository.delete(note)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        parentjob.cancel()
    }
}