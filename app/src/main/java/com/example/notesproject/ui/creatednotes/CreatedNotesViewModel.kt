package com.example.notesproject.ui.creatednotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesproject.Util
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.CreatedNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatedNotesViewModel @Inject constructor(
   private val notesUseCase: CreatedNotesUseCase
) : ViewModel() {

    val _notes : MutableLiveData<ArrayList<Note>> = MutableLiveData()
    val notes : LiveData<ArrayList<Note>> = _notes


    fun getPhotos(){
        viewModelScope.launch{
            val notes = Util.sampleData()
            _notes.value = notes
        }
    }

}