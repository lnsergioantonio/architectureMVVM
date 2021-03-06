package com.bancrea.architectureexample.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var title:String,
    var description:String,
    var priority:Int
){
    constructor():this(null,"","",0)
}