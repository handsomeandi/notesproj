package com.example.notesproject.ui.creatednotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CreatedNoteViewModelFactory @Inject constructor(private val createdNotesViewModel: CreatedNotesViewModel) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = createdNotesViewModel as T
}