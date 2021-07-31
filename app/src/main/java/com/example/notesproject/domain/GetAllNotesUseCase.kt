package com.example.notesproject.domain

import com.example.notesproject.data.model.Note
import com.example.notesproject.data.repository.NotesRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val repository: NotesRepository) {

    fun execute(): List<Note> = repository.getAllNotes()

}