package com.example.notesproject.data

import android.net.Uri
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.data.model.NoteEntity
import com.example.notesproject.domain.model.NoteModel

object NoteMapper {

	fun NoteModel.toEntity(): NoteEntity = NoteEntity(title, noteText, createdDate, updatedDate, images, id)

	fun NoteEntity.toDomain(): NoteModel =
		NoteModel(title, noteText, createdDate, updatedDate, images.toMutableList(), id)

	fun List<Uri>.toImageObjects(): List<ImageObject> {
		val images = mutableListOf<ImageObject>()
		this.forEach {
			images.add(ImageObject("${Math.random() + Math.random()}", it.toString()))
		}
		return images
	}
}