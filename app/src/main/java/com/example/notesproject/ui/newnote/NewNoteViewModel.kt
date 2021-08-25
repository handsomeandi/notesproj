package com.example.notesproject.ui.newnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.Util
import com.example.notesproject.data.model.Note
import javax.inject.Inject

class NewNoteViewModel @Inject constructor() : ViewModel() {
	private val _currentEvent: MutableLiveData<Events> = MutableLiveData(
		Events.Initial
	)
	val currentEvent: LiveData<Events> = _currentEvent

	val note: MutableLiveData<Note> = MutableLiveData(Note(Util.getSampleData().size + 1, "", "", "", "", ""))


	fun onCreate(id: Int) {
		loadNote(id)
	}

	fun onAddPressed() {
		Util.getSampleData().apply {
			note.value?.let{ add(it) }
			Util.setSampleData(this)
		}
		_currentEvent.value = Events.AddPressed
	}

	private fun loadNote(id: Int) {
		Util.getSampleData().find { it.id == id }?.let {
			note.value = it
		}
	}

	sealed class Events {
		object Initial : Events()
		object AddPressed : Events()
	}
}