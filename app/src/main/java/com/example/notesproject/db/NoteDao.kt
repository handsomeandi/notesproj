package com.example.notesproject.db

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM notesTable")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notesTable WHERE id = :id")
    fun getNoteById(id:Int) : Note?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note:Note)

    @Delete
    fun deleteNote(note: Note)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg users: TownClass)
//
//    @Delete
//    fun delete(user: TownClass)
//
//    @Query("DELETE FROM " + Constants.TOWN_TABLE)
//    fun deleteAll()
}