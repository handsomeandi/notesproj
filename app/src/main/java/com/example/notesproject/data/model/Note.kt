package com.example.notesproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
data class Note(
    var title: String,
    var noteText: String,
    val createdDate: String,
    val updatedDate: String,
    var images: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)