package com.example.notesproject.data.di

import com.example.notesproject.data.repository.NotesRepositoryImpl
import com.example.notesproject.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {
	@Binds
	abstract fun bindNoteRepository(noteRepositoryImpl: NotesRepositoryImpl): NotesRepository
}