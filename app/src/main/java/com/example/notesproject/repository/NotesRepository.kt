package com.example.notesproject.repository

import com.example.notesproject.db.Note
import javax.inject.Inject


class NotesRepository @Inject constructor(private val dbHandler: DBHandler) {

    fun getAllNotes() = dbHandler.getAllNotes()

    fun getNoteById(id : Int) = dbHandler.getNoteById(id)

    fun addNote(note: Note) = dbHandler.addNote(note)

    fun deleteNote(note : Note) = dbHandler.deleteNote(note)
}