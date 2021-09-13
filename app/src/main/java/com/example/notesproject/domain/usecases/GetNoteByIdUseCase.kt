package com.example.notesproject.domain.usecases

import com.example.notesproject.domain.repository.NotesRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repository: NotesRepository) {

    fun execute(id: Int) = repository.getNoteById(id)

}