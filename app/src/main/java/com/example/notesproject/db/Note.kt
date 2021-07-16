package com.example.notesproject.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
data class Note (
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title : String,
    var noteText : String,
    val createdDate : String,
    val updatedDate : String,
    var images : String
)