package com.example.notesproject.ui.updatenotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.usecases.GetNoteByIdUseCase
import com.example.notesproject.domain.usecases.UpdateNoteUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UpdateNoteViewModel @Inject constructor(
	private val updateNoteUseCase: UpdateNoteUseCase,
	private val getNoteByIdUseCase: GetNoteByIdUseCase
) : BaseViewModel() {

	private val _currentEvent: MutableLiveData<Events> = MutableLiveData(
		Events.Initial
	)
	val currentEvent: LiveData<Events> = _currentEvent

	val note: MutableLiveData<NoteModel> = MutableLiveData()

	fun onCreate(id: Int) {
		loadNote(id)
	}

	fun onSavePressed() {
		note.value?.let { note ->
			updateNoteUseCase.execute(note).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{ _currentEvent.value = Events.SavePressed },
					{ logErrorMessage(it.message) })
		}
	}

	fun onCancelPressed() {
		_currentEvent.value = Events.CancelPressed
	}

	private fun loadNote(id: Int) {
		getNoteByIdUseCase.execute(id).subscribeIoObserveMain(
			{ note.value = it },
			{ logErrorMessage(it.message) }
		)
	}

	sealed class Events {
		object Initial : Events()
		object SavePressed : Events()
		object CancelPressed : Events()
	}
}
