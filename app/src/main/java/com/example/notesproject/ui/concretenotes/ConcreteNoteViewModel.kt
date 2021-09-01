package com.example.notesproject.ui.concretenotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.DeleteNoteUseCase
import com.example.notesproject.domain.GetNoteByIdUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.subscribeIoObserveMain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject


class ConcreteNoteViewModel @Inject constructor(
	private val getNoteByIdUseCase: GetNoteByIdUseCase,
	private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
	private val _currentEvent: MutableLiveData<Events> = MutableLiveData(Events.Initial)
	val currentEvent: LiveData<Events> = _currentEvent

	val note: MutableLiveData<Note> = MutableLiveData()
//	val note: LiveData<Note> = _note

	fun onCreate(id: Int) {
		loadNote(id)
	}

	fun onDeletePressed() {
		note.value?.id?.let {
			_currentEvent.value = Events.DeletePressed(it)
		}
	}

	fun onDelete() {
		note.value?.let { note ->
			deleteNoteUseCase.execute(note).subscribeOn(io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
				_currentEvent.value = Events.Deleted
			},
				{
					logErrorMessage(it.message)
				})
		}
	}

	fun onUpdatePressed() {
		note.value?.id?.let {
			_currentEvent.value = Events.UpdatePressed(it)
		}
	}

	private fun loadNote(id: Int) {
		getNoteByIdUseCase.execute(id).subscribeIoObserveMain({
			note.value = it
		}, {
			logErrorMessage(it.message)
		}
		)
	}


	sealed class Events {
		object Initial : Events()
		class DeletePressed(id: Int) : Events()
		object Deleted : Events()
		class UpdatePressed(id: Int) : Events()
	}
}