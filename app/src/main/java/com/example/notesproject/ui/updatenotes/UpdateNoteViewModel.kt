package com.example.notesproject.ui.updatenotes

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.usecases.GetNoteByIdUseCase
import com.example.notesproject.domain.usecases.UpdateNoteUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.ImageViewModel
import com.example.notesproject.ui.SingleLiveEvent
import javax.inject.Inject

class UpdateNoteViewModel @Inject constructor(
	private val updateNoteUseCase: UpdateNoteUseCase,
	private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ImageViewModel() {

	private val _currentEvent: SingleLiveEvent<Events> = SingleLiveEvent()
	val currentEvent: SingleLiveEvent<Events> = _currentEvent

	val note: MutableLiveData<NoteModel> = MutableLiveData()

	val images: MutableLiveData<MutableList<ImageObject>> = MutableLiveData()

	fun onCreate(id: Int) {
		loadNote(id)
	}

	fun onImagesReceived(imagesUris: List<ImageObject>, contentResolver: ContentResolver) {
		if (imagesUris.size + (note.value?.images?.size ?: 0) <= 10) {
			saveImages(imagesUris, contentResolver)
				.doOnNext { image ->
					images.postValue(images.value?.apply {
						add(image)
					})
				}.subscribeIoObserveMain({
					_currentEvent.value = Events.ImagesReceived
					Log.d("testing", "success saving")
				},
					{ logErrorMessage(it.message) })

		} else {
			_currentEvent.value = Events.ImagesLimit
		}
	}


	fun onSavePressed(contentResolver: ContentResolver) {
		note.value?.let { note ->
			val updatedNote = NoteModel(note.title, note.noteText, "", "", images.value ?: note.images, note.id)
			updateImages(note.images, updatedNote.images, contentResolver).subscribeIoObserveMain(
				{
					updateNoteUseCase.execute(updatedNote).subscribeIoObserveMain(
						{ _currentEvent.value = Events.SavePressed },
						{ logErrorMessage(it.message) })
				},
				{ logErrorMessage(it.message) }
			)

		}
	}

	fun onSelectImagePressed() {
		_currentEvent.value = Events.ChooseImagePressed
	}

	fun onRemoveImagePressed(image: ImageObject) {
		images.value = images.value?.apply {
			remove(image)
		}
	}

	fun onCancel() {
		removeImages(images.value?.filter {
			note.value?.images?.contains(it) == false
		}).subscribeIoObserveMain(
			{ _currentEvent.value = Events.CancelPressed },
			{ logErrorMessage(it.message) }
		)
	}

	private fun loadNote(id: Int) {
		getNoteByIdUseCase.execute(id).subscribeIoObserveMain(
			{
				note.value = it
				images.value = it.images
			},
			{ logErrorMessage(it.message) }
		)
	}

	sealed class Events {
		object Initial : Events()
		object SavePressed : Events()
		object CancelPressed : Events()
		object ImagesReceived : Events()
		object ImagesLimit : Events()
		object ChooseImagePressed : Events()
	}
}
