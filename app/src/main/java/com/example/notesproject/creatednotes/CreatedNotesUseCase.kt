package com.example.notesproject.creatednotes

import com.example.notesproject.db.Note
import com.example.notesproject.repository.NotesRepository
import javax.inject.Inject

class CreatedNotesUseCase @Inject constructor(private val repository: NotesRepository) {

    fun getAllNotes() = repository.getAllNotes()

    fun getNoteById(id : Int) = repository.getNoteById(id)

    fun addNote(note: Note) = repository.addNote(note)

    fun deleteNote(note : Note) = repository.deleteNote(note)

}