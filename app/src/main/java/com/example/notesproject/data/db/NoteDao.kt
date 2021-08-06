package com.example.notesproject.data.db

import androidx.room.*
import com.example.notesproject.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notesTable")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notesTable WHERE id = :id")
    fun getNoteById(id: Int): Note?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}