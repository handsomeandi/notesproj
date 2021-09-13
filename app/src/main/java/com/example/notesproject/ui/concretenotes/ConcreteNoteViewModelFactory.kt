package com.example.notesproject.ui.concretenotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ConcreteNoteViewModelFactory @Inject constructor(private val concreteNoteViewModel: ConcreteNoteViewModel) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = concreteNoteViewModel as T
}