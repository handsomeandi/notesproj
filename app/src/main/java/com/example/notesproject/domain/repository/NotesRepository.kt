package com.example.notesproject.domain.repository

import com.example.notesproject.domain.model.NoteModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface NotesRepository {
	fun getAllNotes(): Observable<List<NoteModel>>

	fun getNoteById(id: Int): Single<NoteModel>

	fun addNote(noteEntity: NoteModel): Completable

	fun deleteNote(noteEntity: NoteModel): Completable

	fun deleteNotes(notes: List<NoteModel>): Completable

	fun updateNote(noteEntity: NoteModel): Completable
}