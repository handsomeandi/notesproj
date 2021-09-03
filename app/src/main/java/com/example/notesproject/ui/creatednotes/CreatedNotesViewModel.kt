package com.example.notesproject.ui.creatednotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.GetAllNotesUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.subscribeIoObserveMain
import io.reactivex.rxjava3.functions.Consumer
import javax.inject.Inject


class CreatedNotesViewModel @Inject constructor(
	private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

	private val _notes: MutableLiveData<List<Note>> = MutableLiveData(mutableListOf())
	val notes: LiveData<List<Note>>
		get() = _notes

	private val _currentEvent: MutableLiveData<Events> = MutableLiveData(Events.Initial)
	val currentEvent: LiveData<Events> = _currentEvent

	fun onCreate() {
		getNotes()
	}

	fun onNotePressed(id: Int) {
		_currentEvent.value = Events.NotePressed(id)
	}

	fun onAddNotePressed() {
		_currentEvent.value = Events.CreateNotePressed
	}

	private fun getNotes() {
		getAllNotesUseCase.execute().subscribeIoObserveMain(
			{
				_notes.value = it
			},
			{ logErrorMessage(it.message) }
		)
	}

	sealed interface Events {
		object Initial : Events
		class NotePressed(val id: Int) : Events
		object CreateNotePressed : Events
	}
}