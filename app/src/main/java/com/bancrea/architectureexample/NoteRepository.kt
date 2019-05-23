package com.bancrea.architectureexample

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class NoteRepository(private val notedao:NoteDao){

    var allNotes:LiveData<List<Note>> = notedao.getAllNotes()

    @WorkerThread
    suspend fun insert(note:Note){
        notedao.insert(note)
    }
    @WorkerThread
    fun update(note:Note){
        GlobalScope.launch {
            notedao.update(note)
        }
    }
    @WorkerThread
    fun delete(note:Note){
        GlobalScope.launch {
            notedao.delete(note)
        }
    }
    @WorkerThread
    fun deleteAll(){
        GlobalScope.launch {
            notedao.deleteNotes()
        }
    }
}