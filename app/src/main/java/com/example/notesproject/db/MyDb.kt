package com.example.notesproject.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class MyDB : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}