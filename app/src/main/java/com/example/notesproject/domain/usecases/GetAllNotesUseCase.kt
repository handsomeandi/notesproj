package com.example.notesproject.domain.usecases

import com.example.notesproject.domain.repository.NotesRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val repository: NotesRepository) {

    fun execute() = repository.getAllNotes()

}