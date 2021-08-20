package com.example.notesproject.ui.newnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notesproject.R


class NewNoteFragment : Fragment() {

    companion object {
        fun newInstance() = NewNoteFragment()
    }

    private val viewModel: NewNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_note_fragment, container, false)
    }

}