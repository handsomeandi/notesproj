package com.example.notesproject.data.db

import androidx.room.*
import com.example.notesproject.data.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NoteDao {
    @Query("SELECT * FROM notesTable")
    fun getAllNotes(): Single<List<Note>>

    @Query("SELECT * FROM notesTable WHERE id = :id")
    fun getNoteById(id: Int): Single<Note>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note) : Completable

    @Delete
    fun deleteNote(note: Note) : Completable

    @Update
    fun updateNote(note: Note) : Completable
}