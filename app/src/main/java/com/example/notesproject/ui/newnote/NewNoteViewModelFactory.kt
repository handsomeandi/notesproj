package com.example.notesproject.ui.newnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class NewNoteViewModelFactory @Inject constructor(private val newNoteViewModel: NewNoteViewModel) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = newNoteViewModel as T
}