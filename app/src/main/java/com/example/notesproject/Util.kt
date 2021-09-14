package com.example.notesproject

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.io.File

object Util {

	private const val imageFolder = "noteImgDir"

	val IMAGE_DIRECTORY: File = MainApp.instance.applicationContext.getDir(imageFolder, Context.MODE_PRIVATE)

}

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

infix fun View.clicker(block: () -> Unit) = setOnClickListener { block() }


fun logErrorMessage(message: String?) {
	Log.d("testing", message ?: "unknown error")
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

