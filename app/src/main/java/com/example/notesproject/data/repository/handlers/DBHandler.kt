package com.example.notesproject.data.repository.handlers

import com.example.notesproject.data.db.NoteDao
import com.example.notesproject.data.model.Note
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class DBHandler @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes(): Observable<List<Note>> = dao.getAllNotes()

    fun getNoteById(id: Int) = dao.getNoteById(id)

    fun addNote(note: Note) = dao.addNote(note)

    fun deleteNote(note: Note) = dao.deleteNote(note)

    fun updateNote(note: Note) = dao.updateNote(note)
}