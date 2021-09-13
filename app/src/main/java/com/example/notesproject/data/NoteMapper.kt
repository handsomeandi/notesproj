package com.example.notesproject.data

import com.example.notesproject.data.model.NoteEntity
import com.example.notesproject.domain.model.NoteModel

object NoteMapper {

	fun NoteModel.toEntity(): NoteEntity = NoteEntity(title, noteText, createdDate, updatedDate, images, id)

	fun NoteEntity.toDomain(): NoteModel = NoteModel(title, noteText, createdDate, updatedDate, images, id)
}