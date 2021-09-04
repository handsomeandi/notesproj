package com.example.notesproject.ui.newnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.Util
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.AddNoteUseCase
import com.example.notesproject.domain.GetNoteByIdUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.subscribeIoObserveMain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject

class NewNoteViewModel @Inject constructor(
	private val getNoteByIdUseCase: GetNoteByIdUseCase,
	private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {
	private val _currentEvent: MutableLiveData<Events> = MutableLiveData(
		Events.Initial
	)
	val currentEvent: LiveData<Events> = _currentEvent

	val title: MutableLiveData<String> = MutableLiveData()

	val body: MutableLiveData<String> = MutableLiveData()

	fun onAddPressed() {
		val note = Note(title.value ?: "", body.value ?: "", "", "", "")
		if(note.title.isEmpty() || note.noteText.isEmpty()) return
		addNoteUseCase.execute(note).subscribeOn(io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
				{ _currentEvent.value = Events.AddPressed },
				{ logErrorMessage(it.message) }
			)
	}

	fun onSelectImagePressed(){
		_currentEvent.value = Events.ChooseImagePressed
	}

	sealed class Events {
		object Initial : Events()
		object AddPressed : Events()
		object ChooseImagePressed : Events()
	}
}