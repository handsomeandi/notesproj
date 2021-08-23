package com.example.notesproject.ui.updatenotes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.Util
import com.example.notesproject.data.model.Note
import javax.inject.Inject

class UpdateNoteViewModel @Inject constructor() : ViewModel() {

    private val _currentEvent: MutableLiveData<Events> = MutableLiveData(
        Events.Initial
    )
    val currentEvent: LiveData<Events> = _currentEvent

    private val _note: MutableLiveData<Note> = MutableLiveData()
    val note: LiveData<Note> = _note

    fun onCreate(id: Int) {
        loadNote(id)
    }

    fun onSavePressed(title: String, body: String) {
        val notes = Util.getSampleData().map {
            if (it.id == _note.value?.id) it.apply {
                this.title = title
                this.noteText = body
            } else it
        }
        Util.setSampleData(notes)
        _note.value = _note.value?.apply {
            this.title = title
            this.noteText = body
        }
        _currentEvent.value = Events.SavePressed
    }

    fun onCancelPressed() {
        _currentEvent.value = Events.CancelPressed
    }

    private fun loadNote(id: Int) {
        Util.getSampleData().find { it.id == id }?.let {
            _note.value = it
        }
    }

    sealed class Events {
        object Initial : Events()
        object SavePressed : Events()
        object CancelPressed : Events()
    }
}
