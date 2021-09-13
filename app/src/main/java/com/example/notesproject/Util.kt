package com.example.notesproject

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.notesproject.data.model.ImageObject
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

object Util {

	private const val imageFolder = "noteImgDir"

	val IMAGE_DIRECTORY: File = MainApp.instance.applicationContext.getDir(imageFolder, Context.MODE_PRIVATE)

	@Suppress("DEPRECATION")
	fun Activity.setStatusBarColor(@ColorRes statusBarColor: Int, lightTextColor: Boolean) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
			window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
			if (lightTextColor) window.decorView.systemUiVisibility =
				0 else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
			window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
		}
	}
}

infix fun View.clicker(onClickListener: View.OnClickListener) {
	setOnClickListener(onClickListener)
}


fun logErrorMessage(message: String?) {
	Log.d("testing", message ?: "unknown error")
}

fun List<Uri>.toImageObjects(): List<ImageObject> {
	val images = mutableListOf<ImageObject>()
	this.forEach {
		images.add(ImageObject("${Math.random() + Math.random()}", it.toString()))
	}
	return images
}

infix fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
	this.addTextChangedListener(object : TextWatcher {
		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			return
		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			return
		}

		override fun afterTextChanged(s: Editable?) {
			onTextChanged(s.toString())
		}
	})
}

class SingleLiveEvent<T> : MutableLiveData<T>() {
	private val mPending = AtomicBoolean(false)

	@MainThread
	override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
		if (hasActiveObservers()) {
			Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
		}
		// Observe the internal MutableLiveData
		super.observe(owner, { t ->
			if (mPending.compareAndSet(true, false)) {
				observer.onChanged(t)
			}
		})
	}

	@MainThread
	override fun setValue(t: T?) {
		mPending.set(true)
		super.setValue(t)
	}

	/**
	 * Used for cases where T is Void, to make calls cleaner.
	 */
	@MainThread
	fun call() {
		value = null
	}

	companion object {
		private const val TAG = "SingleLiveEvent"
	}
}