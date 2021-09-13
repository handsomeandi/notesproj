package com.example.notesproject.domain.model

import com.example.notesproject.data.model.ImageObject

data class NoteModel(
	var title: String,
	var noteText: String,
	val createdDate: String,
	val updatedDate: String,
	var images: List<ImageObject>,
	val id: Int = 0
)