package com.example.notesproject.domain

import com.example.notesproject.data.model.Note
import com.example.notesproject.data.repository.NotesRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: NotesRepository) {

	fun execute(note: Note) = repository.updateNote(note)
}