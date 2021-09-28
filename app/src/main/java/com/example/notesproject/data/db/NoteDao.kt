package com.example.notesproject.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesproject.data.model.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface NoteDao {
	@Query("SELECT * FROM notesTable")
	fun getAllNotes(): Observable<List<NoteEntity>>

	@Query("SELECT * FROM notesTable WHERE id = :id")
	fun getNoteById(id: Int): Single<NoteEntity>


	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addNote(noteEntity: NoteEntity): Completable

	@Delete
	fun deleteNote(noteEntity: NoteEntity): Completable

	@Delete
	fun deleteNotes(notes: List<NoteEntity>): Completable

	@Update
	fun updateNote(noteEntity: NoteEntity): Completable
}