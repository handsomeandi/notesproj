package com.example.notesproject.data.repository.handlers

import com.example.notesproject.data.model.Note
import com.example.notesproject.data.db.NoteDao
import javax.inject.Inject


class DBHandler @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes() = dao.getAllNotes()

    fun getNoteById(id : Int) = dao.getNoteById(id)

    fun addNote(note: Note) = dao.addNote(note)

    fun deleteNote(note : Note) = dao.deleteNote(note)
}