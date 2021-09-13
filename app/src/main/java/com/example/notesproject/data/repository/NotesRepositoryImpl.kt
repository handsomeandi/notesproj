package com.example.notesproject.data.repository

import com.example.notesproject.data.repository.handlers.LocalNoteSource
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.repository.NotesRepository
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(private val localNoteSource: LocalNoteSource) : NotesRepository {

    override fun getAllNotes() = localNoteSource.getAllNotes()

    override fun getNoteById(id: Int) = localNoteSource.getNoteById(id)

    override fun addNote(noteEntity: NoteModel) = localNoteSource.addNote(noteEntity)

    override fun deleteNote(noteEntity: NoteModel) = localNoteSource.deleteNote(noteEntity)

    override fun updateNote(noteEntity: NoteModel) = localNoteSource.updateNote(noteEntity)

}