package com.example.notesproject.data.repository.handlers

import com.example.notesproject.data.NoteMapper.toDomain
import com.example.notesproject.data.NoteMapper.toEntity
import com.example.notesproject.data.db.NoteDao
import com.example.notesproject.domain.model.NoteModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class LocalNoteSource @Inject constructor(private val dao: NoteDao) {

	fun getAllNotes(): Observable<List<NoteModel>> = dao.getAllNotes().map { list ->
		list.map { it.toDomain() }
	}

	fun getNoteById(id: Int): Single<NoteModel> = dao.getNoteById(id).map {
		it.toDomain()
	}

	fun addNote(noteModel: NoteModel) = dao.addNote(noteModel.toEntity())

	fun deleteNote(noteModel: NoteModel) = dao.deleteNote(noteModel.toEntity())

	fun deleteNotes(notes: List<NoteModel>) = dao.deleteNotes(notes.map { it.toEntity() })

	fun updateNote(noteModel: NoteModel) = dao.updateNote(noteModel.toEntity())
}