package com.example.notesproject.data.di

import android.content.Context
import androidx.room.Room
import com.example.notesproject.data.db.MyDB
import com.example.notesproject.data.db.NoteDao
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
        return Room.databaseBuilder(context, MyDB::class.java, "DB_NAME").allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(db: MyDB): NoteDao {
        return db.noteDao()
    }

}