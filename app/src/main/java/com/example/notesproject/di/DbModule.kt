package com.example.notesproject.di

import android.content.Context
import androidx.room.Room
import com.example.notesproject.db.MyDB
import com.example.notesproject.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context): MyDB {
        return Room.databaseBuilder(context, MyDB::class.java, "DB_NAME").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(db: MyDB): NoteDao {
        return db.noteDao()
    }

}