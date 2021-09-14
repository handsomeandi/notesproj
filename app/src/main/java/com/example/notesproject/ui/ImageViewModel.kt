package com.example.notesproject.ui

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.net.toUri
import com.example.notesproject.Util
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.logErrorMessage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

abstract class ImageViewModel : BaseViewModel() {

	fun saveImages(images: List<ImageObject>?, contentResolver: ContentResolver): Observable<ImageObject> {
		return Observable.create { mainEmitter ->
			try {
				if (images?.isNotEmpty() == true) {
					Single.zip(images.map { image ->
						saveImageSingle(image, contentResolver).doOnSuccess {
							mainEmitter.onNext(it)
						}.subscribeOn(Schedulers.io())
					}) {
						mainEmitter.onComplete()
					}.subscribe(
						{ logErrorMessage("all singles completed") },
						{ logErrorMessage("error") }
					)
				} else {
					mainEmitter.onComplete()
				}
			} catch (e: Exception) {
				mainEmitter.onError(e)
			}
		}
	}


	fun removeImages(images: List<ImageObject>?): Completable {
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

	fun removeImage(image: ImageObject): Completable {
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


	fun updateImages(
		oldImages: List<ImageObject>,
		newImages: List<ImageObject>,
		contentResolver: ContentResolver
	): Completable {
		return Completable.create { mainEmitter ->
			saveImagesCompletable(newImages.filter {
				!File(Util.IMAGE_DIRECTORY, "${it.id}.png").exists()
			}, contentResolver).andThen(
				removeImages(oldImages.filter { image ->
					!newImages.contains(image)
				}).subscribeOn(Schedulers.io())
			).subscribe(
				{ mainEmitter.onComplete() },
				{ logErrorMessage(it.message) }
			)
		}
	}

	private fun saveImagesCompletable(images: List<ImageObject>, contentResolver: ContentResolver) =
		Completable.create { saveEmitter ->
			try {
				saveImages(images, contentResolver).doOnComplete {
					saveEmitter.onComplete()
				}.subscribe()
			} catch (e: Exception) {
				saveEmitter.onError(e)
			}
		}.subscribeOn(Schedulers.io())


	private fun saveImageSingle(image: ImageObject, contentResolver: ContentResolver) =
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
		}

}