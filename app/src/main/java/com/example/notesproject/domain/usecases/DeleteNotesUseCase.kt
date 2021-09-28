package com.example.notesproject.domain.usecases

import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(private val repository: NotesRepository) {

	fun execute(notes: List<NoteModel>) = repository.deleteNotes(notes)
}