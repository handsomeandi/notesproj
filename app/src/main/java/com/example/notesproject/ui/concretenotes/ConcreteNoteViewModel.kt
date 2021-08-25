package com.example.notesproject.ui.concretenotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.Util
import com.example.notesproject.data.model.Note
import javax.inject.Inject


class ConcreteNoteViewModel @Inject constructor() : ViewModel() {
    private val _currentEvent: MutableLiveData<Events> = MutableLiveData(Events.Initial)
    val currentEvent: LiveData<Events> = _currentEvent

    private val _note: MutableLiveData<Note> = MutableLiveData()
    val note: LiveData<Note> = _note

    fun onCreate(id: Int) {
        loadNote(id)
    }

    fun onDeletePressed() {
        note.value?.id?.let {
            _currentEvent.value = Events.DeletePressed(it)
        }
    }

    fun onDelete(){
        Util.getSampleData().apply {
            remove(_note.value)
            Util.setSampleData(this)
            _currentEvent.value = Events.Deleted
        }
    }

    fun onUpdatePressed() {
        note.value?.id?.let {
            _currentEvent.value = Events.UpdatePressed(it)
        }
    }

    private fun loadNote(id: Int) {
        Util.getSampleData().find { it.id == id }?.let {
            _note.value = it
        }
    }


    sealed class Events {
        object Initial : Events()
        class DeletePressed(id: Int) : Events()
        object Deleted : Events()
        class UpdatePressed(id: Int) : Events()
    }
}