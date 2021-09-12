package com.example.notesproject.ui.newnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.AddNoteUseCase
import com.example.notesproject.domain.GetNoteByIdUseCase
import com.example.notesproject.logErrorMessage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject


class NewNoteViewModel : ViewModel() {
    private val _currentEvent: MutableLiveData<Events> = MutableLiveData(
        Events.Initial
    )
    val currentEvent: LiveData<Events> = _currentEvent

    val title: MutableLiveData<String> = MutableLiveData()

    val body: MutableLiveData<String> = MutableLiveData()

    val images: MutableLiveData<MutableList<ImageObject>> = MutableLiveData(mutableListOf())

    @Inject
    lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Inject
    lateinit var addNoteUseCase: AddNoteUseCase


    fun onAddPressed() {
        val note = Note(title.value ?: "", body.value ?: "", "", "", images.value ?: listOf())
        if (note.title.isEmpty() || note.noteText.isEmpty()) return
        addNoteUseCase.execute(note).subscribeOn(io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _currentEvent.value = Events.AddPressed(images.value) },
                { logErrorMessage(it.message) }
            )
    }


    fun onSelectImagePressed() {
        _currentEvent.value = Events.ChooseImagePressed
    }

    fun onImagesReceived(imagesUris: List<ImageObject>) {
        if (imagesUris.size + (images.value?.size ?: 0) <= 10) {
            images.value = images.value?.apply {
                addAll(imagesUris.filter { img ->
                    img !in (this.asSequence().filter { it.uri != img.uri })
                })
            }
            _currentEvent.value = Events.ImagesReceived
        } else {
            _currentEvent.value = Events.ImagesLimit
        }
    }

    fun onRemoveImagePressed(id: String) {
        images.value = images.value?.apply {
            remove(this.find { it.id == id })
        }
    }

    fun onBackPressed() {
        _currentEvent.value = Events.BackPressed(images.value)
    }

    fun onNoteTitleChanged(noteTitle: String) {
        if (noteTitle != title.value) title.value = noteTitle
    }

    fun onNoteBodyChanged(noteBody: String) {
        if (noteBody != body.value) body.value = noteBody
    }

    fun onImagesSaved() {
    }

    sealed class Events {
        object Initial : Events()
        class AddPressed(val images: List<ImageObject>?) : Events()
        class BackPressed(val images: List<ImageObject>?) : Events()
        object ChooseImagePressed : Events()
        object ImagesReceived : Events()
        object ImagesLimit : Events()
    }
}