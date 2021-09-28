package com.example.notesproject.ui.creatednotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.usecases.DeleteNotesUseCase
import com.example.notesproject.domain.usecases.GetAllNotesUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.SingleLiveEvent
import com.example.notesproject.ui.base.BaseViewModel
import javax.inject.Inject


class CreatedNotesViewModel @Inject constructor(
	private val getAllNotesUseCase: GetAllNotesUseCase,
	private val deleteNotesUseCase: DeleteNotesUseCase,
) : BaseViewModel() {

	private val _notes: MutableLiveData<List<NoteModel>> = MutableLiveData(mutableListOf())
	val notes: LiveData<List<NoteModel>>
		get() = _notes

	private val _currentEvent: SingleLiveEvent<Events> = SingleLiveEvent()
	val currentEvent: SingleLiveEvent<Events> = _currentEvent

	fun onCreate() {
		getNotes()
	}

	fun onNotePressed(id: Int) {
		_currentEvent.value = Events.NotePressed(id)
	}

	fun onAddNotePressed() {
		_currentEvent.value = Events.CreateNotePressed
	}

	fun onDeletePressed(notesList: List<NoteModel>) {
		_currentEvent.value = Events.DeletePressed(notesList)
	}

	fun onDelete(notesList: List<NoteModel>) {
		deleteNotesUseCase.execute(notesList).subscribeIoObserveMain(
			{
				_currentEvent.value = Events.Deleted
				_notes.value = _notes.value?.filter { note ->
					!notesList.contains(note)
				}
			},
			{ logErrorMessage(it.message) }
		)
	}

	private fun getNotes() {
		getAllNotesUseCase.execute().subscribeIoObserveMain(
			{
				_notes.value = it
			},
			{ logErrorMessage(it.message) }
		)
	}

	sealed class Events {
		object Initial : Events()
		class NotePressed(val id: Int) : Events()
		object CreateNotePressed : Events()
		class DeletePressed(val notesList: List<NoteModel>) : Events()
		object Deleted : Events()
	}
}