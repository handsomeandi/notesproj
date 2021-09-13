package com.example.notesproject.data.di

import android.app.Application
import androidx.room.Room
import com.example.notesproject.data.db.NoteDB
import com.example.notesproject.data.db.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DbModule(private val app: Application){

    @Singleton
    @Provides
    fun provideDB(): NoteDB {
        return Room.databaseBuilder(app, NoteDB::class.java, "DB_NAME").build()
    }

    @Singleton
    @Provides
    fun provideDao(db: NoteDB): NoteDao {
        return db.noteDao()
    }
}