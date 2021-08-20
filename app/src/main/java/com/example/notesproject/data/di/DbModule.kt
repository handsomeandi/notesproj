package com.example.notesproject.data.di

import android.app.Application
import androidx.room.Room
import com.example.notesproject.data.db.MyDB
import com.example.notesproject.data.db.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DbModule(private val app: Application){

    @Singleton
    @Provides
    fun provideDB(): MyDB {
        return Room.databaseBuilder(app, MyDB::class.java, "DB_NAME").build()
    }

    @Singleton
    @Provides
    fun provideDao(db: MyDB): NoteDao {
        return db.noteDao()
    }
}