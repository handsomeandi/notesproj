package com.example.notesproject.domain

import com.example.notesproject.data.repository.NotesRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repository: NotesRepository) {

    fun execute(id: Int) = repository.getNoteById(id)

}