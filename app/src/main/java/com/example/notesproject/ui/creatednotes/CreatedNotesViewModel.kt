package com.example.notesproject.ui.creatednotes

import androidx.core.view.KeyEventDispatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesproject.Util
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.CreatedNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class CreatedNotesViewModel @Inject constructor(
   private val notesUseCase: CreatedNotesUseCase
) : ViewModel() {

    private val _notes : MutableLiveData<ArrayList<Note>> = MutableLiveData()
    val notes : LiveData<ArrayList<Note>> = _notes

    private val _currentEvent : MutableLiveData<Events> = MutableLiveData(Events.Initial)
    val currentEvent: LiveData<Events> = _currentEvent

    fun onCreate(){
        getPhotos()
    }

    fun onNotePressed(id: Int){
        _currentEvent.value = Events.NotePressed(id)
    }

    private fun getPhotos(){
        val notes = Util.sampleData()
        _notes.value =  notes
    }

    sealed interface Events{
        object Initial : Events
        class NotePressed(val id: Int) : Events
    }
}