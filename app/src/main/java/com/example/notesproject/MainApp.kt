package com.example.notesproject


import android.app.Application
import com.example.notesproject.data.di.AppComponent
import com.example.notesproject.data.di.DaggerAppComponent
import com.example.notesproject.data.di.DbModule


class MainApp : Application() {
    var appComponent: AppComponent? = null

    companion object {
        lateinit var instance: MainApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().dbModule(DbModule(this)).build()
    }
}