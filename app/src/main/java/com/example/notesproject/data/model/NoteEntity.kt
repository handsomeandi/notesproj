package com.example.notesproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "notesTable")
data class NoteEntity(
	var title: String,
	var noteText: String,
	val createdDate: String,
	val updatedDate: String,
	var images: List<ImageObject>,
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
)

data class ImageObject(
	val id: String,
	val uri: String
)

class Converter {
	@TypeConverter
	fun convertImagesToJson(imageObject: List<ImageObject>): String =
		Gson().toJson(imageObject)

	@TypeConverter
	fun convertJsonToImages(json: String): List<ImageObject> {
		val listType = object : TypeToken<List<ImageObject>>() {}.type
		return Gson().fromJson(json, listType)
	}
}
