package com.example.notesproject.concretenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notesproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConcreteNoteFragment : Fragment() {

    companion object {
        fun newInstance() = ConcreteNoteFragment()
    }

    //TODO: inject
    private lateinit var viewModel: ConcreteNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.concrete_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}