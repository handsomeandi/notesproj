package com.example.notesproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesproject.data.model.Converter
import com.example.notesproject.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class NoteDB : RoomDatabase() {
	abstract fun noteDao(): NoteDao
}