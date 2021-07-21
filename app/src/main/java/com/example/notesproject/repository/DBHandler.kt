package com.example.notesproject.repository

import com.example.notesproject.db.Note
import com.example.notesproject.db.NoteDao
import javax.inject.Inject


class DBHandler @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes() = dao.getAllNotes()

    fun getNoteById(id : Int) = dao.getNoteById(id)

    fun addNote(note: Note) = dao.addNote(note)

    fun deleteNote(note : Note) = dao.deleteNote(note)
}