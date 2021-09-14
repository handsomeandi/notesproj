package com.example.notesproject.ui.concretenotes

import androidx.lifecycle.MutableLiveData
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.usecases.DeleteNoteUseCase
import com.example.notesproject.domain.usecases.GetNoteByIdUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.BaseViewModel
import com.example.notesproject.ui.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject


class ConcreteNoteViewModel @Inject constructor(
	private val getNoteByIdUseCase: GetNoteByIdUseCase,
	private val deleteNoteUseCase: DeleteNoteUseCase
) : BaseViewModel() {

	private val _currentEvent: SingleLiveEvent<in Events> = SingleLiveEvent()
	val currentEvent
		get() = _currentEvent

	val note: MutableLiveData<NoteModel> = MutableLiveData()

	fun onCreate(id: Int) {
		loadNote(id)
	}

	fun onDeletePressed() {
		note.value?.id?.let {
			_currentEvent.value = Events.DeletePressed
		}
	}

	fun onDelete() {
		note.value?.let { note ->
			deleteNoteUseCase.execute(note).subscribeOn(io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
				{ _currentEvent.value = Events.Deleted },
				{ logErrorMessage(it.message) }
			)
		}
	}

	fun onUpdatePressed() {
		note.value?.id?.let {
			_currentEvent.value = Events.UpdatePressed
		}
	}

	private fun loadNote(id: Int) {
		getNoteByIdUseCase.execute(id).subscribeIoObserveMain(
			{ note.value = it },
			{ logErrorMessage(it.message) }
		)
	}


	sealed class Events {
		object Initial : Events()
		object UpdatePressed : Events()
		object DeletePressed : Events()
		object Deleted : Events()
	}
}