package com.example.notesproject


import android.app.Application
import com.example.notesproject.data.di.DaggerAppComponent


object MainApp : Application() {
    val appComponent = DaggerAppComponent.create()
}