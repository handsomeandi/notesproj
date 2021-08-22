package com.example.notesproject.data.di

import com.example.notesproject.ui.concretenotes.ConcreteNoteFragment
import com.example.notesproject.ui.creatednotes.CreatedNotesFragment
import com.example.notesproject.ui.updatenotes.UpdateNoteFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(DbModule::class))
interface AppComponent {
    fun inject(fragment: CreatedNotesFragment)
    fun inject(fragment: ConcreteNoteFragment)
    fun inject(fragment: UpdateNoteFragment)
}
