package com.example.notesproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesproject.data.model.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class MyDB : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}