package com.example.notesproject.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.notesproject.R
import com.example.notesproject.ui.newnote.NewNoteFragment


class MainActivity : AppCompatActivity() {

    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout = findViewById(R.id.frameLay)
        supportFragmentManager.beginTransaction().add(R.id.frameLay, NewNoteFragment.newInstance())
            .commit()
    }
}