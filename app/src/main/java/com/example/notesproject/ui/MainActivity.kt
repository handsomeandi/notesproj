package com.example.notesproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.notesproject.R
import com.example.notesproject.ui.creatednotes.CreatedNotesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout = findViewById(R.id.frameLay)
        supportFragmentManager.beginTransaction().add(R.id.frameLay, CreatedNotesFragment.newInstance()).commit()
    }
}