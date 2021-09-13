package com.example.notesproject.ui.newnote

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesproject.SingleLiveEvent
import com.example.notesproject.Util
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.domain.model.NoteModel
import com.example.notesproject.domain.usecases.AddNoteUseCase
import com.example.notesproject.domain.usecases.GetNoteByIdUseCase
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers.io
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class NewNoteViewModel @Inject constructor(
	private val getNoteByIdUseCase: GetNoteByIdUseCase,
	private val addNoteUseCase: AddNoteUseCase
) : BaseViewModel() {
	private val _currentEvent: SingleLiveEvent<Events> = SingleLiveEvent()
	val currentEvent: SingleLiveEvent<Events> = _currentEvent

	val title: MutableLiveData<String> = MutableLiveData()

	val body: MutableLiveData<String> = MutableLiveData()

	val images: MutableLiveData<MutableList<ImageObject>> = MutableLiveData(mutableListOf())


	fun onAddPressed() {
		val note = NoteModel(title.value ?: "", body.value ?: "", "", "", images.value ?: listOf())
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
				}.doOnComplete {
					images.postValue(imagesUris.toMutableList())
				}
				.subscribeIoObserveMain({ Log.d("testing", "success saving") },
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
		deleteImages(images.value).subscribeIoObserveMain(
			{ _currentEvent.value = Events.BackPressed(images.value) },
			{
				logErrorMessage(it.message)
				_currentEvent.value = Events.BackPressed(images.value)
			}
		)
	}

	fun onNoteTitleChanged(noteTitle: String) {
		if (noteTitle != title.value) title.value = noteTitle
	}

	fun onNoteBodyChanged(noteBody: String) {
		if (noteBody != body.value) body.value = noteBody
	}

	fun onImagesSaved() {
	}


	private fun saveImages(images: List<ImageObject>?, contentResolver: ContentResolver): Observable<ImageObject> {
		return Observable.create { mainEmitter ->
			try {
				if (images?.isNotEmpty() == true) {
					images.forEach { image ->
						Single.create<ImageObject> {
							val path = File(Util.IMAGE_DIRECTORY, "${image.id}.png")

							val fos = FileOutputStream(path)
							val isDecoded = BitmapFactory.decodeStream(
								contentResolver.openInputStream(image.uri.toUri()),
								null,
								null
							)?.compress(Bitmap.CompressFormat.PNG, 100, fos)
							try {
								fos.close()
							} catch (e: IOException) {
								e.printStackTrace()
								it.onError(e)
							}
							isDecoded?.let { isSuccessDecoded ->
								if (isSuccessDecoded) it.onSuccess(image)
							}
						}.subscribeOn(io()).subscribe(
							{
								mainEmitter.onNext(it)
								if (it == images.last()) {
									mainEmitter.onComplete()
								}
							},
							{ logErrorMessage(it.message) }
						)
					}
				} else {
					mainEmitter.onComplete()
				}
			} catch (e: Exception) {
				mainEmitter.onError(e)
			}
		}
	}

	private fun deleteImages(images: List<ImageObject>?): Completable {
		return Completable.create {
			try {
				images?.forEach { image ->
					val fileToDelete = File(Util.IMAGE_DIRECTORY, "${image.id}.png")
					if (fileToDelete.exists()) {
						if (fileToDelete.delete()) {
							Log.d("testing", "file Deleted :")
						} else {
							Log.d("testing", "file not Deleted :")
						}
					}
				}
				it.onComplete()

			} catch (e: Exception) {
				it.onError(e)
			}
		}
	}

	private fun removeImage(image: ImageObject): Completable {
		return Completable.create {
			try {
				val fileToDelete = File(Util.IMAGE_DIRECTORY, "${image.id}.png")
				if (fileToDelete.exists()) {
					if (fileToDelete.delete()) {
						it.onComplete()
						Log.d("testing", "file Deleted :")
					} else {
						it.onError(Exception("file not Deleted"))
						Log.d("testing", "file not Deleted :")
					}
				}

			} catch (e: Exception) {
				it.onError(e)
			}
		}
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