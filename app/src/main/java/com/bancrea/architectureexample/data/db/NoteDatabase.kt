package com.bancrea.architectureexample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version=1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private val DATABASE_NAME = "note_database"
        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getNoteDatabase(context: Context): NoteDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(context,
                        NoteDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDataBase(){
            INSTANCE =null
        }
    }
}