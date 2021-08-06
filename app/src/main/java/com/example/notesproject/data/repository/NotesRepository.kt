package com.example.notesproject.data.repository

import com.example.notesproject.data.repository.handlers.DBHandler
import com.example.notesproject.data.model.Note
import javax.inject.Inject


class NotesRepository @Inject constructor(private val dbHandler: DBHandler) {

    fun getAllNotes() = dbHandler.getAllNotes()

    fun getNoteById(id: Int) = dbHandler.getNoteById(id)

    fun addNote(note: Note) = dbHandler.addNote(note)

    fun deleteNote(note: Note) = dbHandler.deleteNote(note)
}