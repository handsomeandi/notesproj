package com.example.notesproject

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.notesproject.data.model.Note

object Util {
    fun sampleData(): ArrayList<Note> {
        return arrayListOf(
            Note(
                1,
                "Title1",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                "30.05.2002",
                "15.07.2002",
                "none"
            ),
            Note(
                2,
                "Title2",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                "30.05.2002",
                "15.07.2002",
                "none"
            ),
            Note(
                3,
                "Title3",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                "30.05.2002",
                "15.07.2002",
                "none"
            ),
            Note(
                4,
                "Title4",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                "30.05.2002",
                "15.07.2002",
                "none"
            ),
            Note(
                5,
                "Title5",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                "30.05.2002",
                "15.07.2002",
                "none"
            ),
            Note(
                6,
                "Title6",
                "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                "30.05.2002",
                "15.07.2002",
                "none"
            )
        )
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
}
