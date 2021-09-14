package com.example.notesproject.ui.newnote

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.usecases.AddNoteUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.base.ImageViewModel
import com.example.notesproject.ui.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject


class NewNoteViewModel @Inject constructor(
	private val addNoteUseCase: AddNoteUseCase
) : ImageViewModel() {
	private val _currentEvent: SingleLiveEvent<Events> = SingleLiveEvent()
	val currentEvent: SingleLiveEvent<Events> = _currentEvent

	val title: MutableLiveData<String> = MutableLiveData()

	val body: MutableLiveData<String> = MutableLiveData()

	val images: MutableLiveData<MutableList<ImageObject>> = MutableLiveData(mutableListOf())


	fun onAddPressed() {
		val note = NoteModel(title.value ?: "", body.value ?: "", "", "", images.value ?: mutableListOf())
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

	fun onImagesReceived(imagesUris: List<ImageObject>, contentResolver: ContentResolver) {
		if (imagesUris.size + (images.value?.size ?: 0) <= 10) {
			saveImages(imagesUris, contentResolver)
				.doOnNext { image ->
					images.postValue(images.value?.apply { add(image) })
				}.subscribeIoObserveMain({ Log.d("testing", "success saving") },
					{ logErrorMessage(it.message) })

			_currentEvent.value = Events.ImagesReceived
		} else {
			_currentEvent.value = Events.ImagesLimit
		}
	}

	fun onRemoveImagePressed(image: ImageObject) {
		removeImage(image).subscribeIoObserveMain(
			{
				images.value = images.value?.apply {
					remove(image)
				}
			},
			{ logErrorMessage(it.message) }
		)

	}

	fun onBackPressed() {
		removeImages(images.value).subscribeIoObserveMain(
			{ _currentEvent.value = Events.BackPressed(images.value) },
			{
				logErrorMessage(it.message)
				_currentEvent.value = Events.BackPressed(images.value)
			}
		)
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