package com.example.notesproject.data.di

import android.app.Application
import com.example.notesproject.ui.creatednotes.CreatedNotesFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(DbModule::class))
interface AppComponent {
    fun inject(fragment: CreatedNotesFragment)
}
