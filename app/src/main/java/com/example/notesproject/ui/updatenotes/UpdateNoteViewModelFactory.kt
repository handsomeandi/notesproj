package com.example.notesproject.ui.updatenotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class UpdateNoteViewModelFactory @Inject constructor(private val updateNoteViewModel: UpdateNoteViewModel) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = updateNoteViewModel as T
}