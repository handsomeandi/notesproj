	package com.example.notesproject.domain

import com.example.notesproject.data.model.Note
import com.example.notesproject.data.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val repository: NotesRepository) {

	fun execute(note: Note) = repository.addNote(note)
}