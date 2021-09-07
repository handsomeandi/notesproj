package com.example.notesproject.ui.newnote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesproject.Util.IMAGE_DIRECTORY
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.data.model.Note
import com.example.notesproject.domain.AddNoteUseCase
import com.example.notesproject.domain.GetNoteByIdUseCase
import com.example.notesproject.logErrorMessage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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

	val images: MutableLiveData<MutableList<ImageObject>> = MutableLiveData(mutableListOf())


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
				addAll(imagesUris)
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

	fun onBackPressed(){
		_currentEvent.value = Events.BackPressed(images.value)
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