package com.example.notesproject.ui.updatenotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notesproject.R


class UpdateNoteFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateNoteFragment()
    }

    // TODO: inject the ViewModel
    private lateinit var viewModel: UpdateNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}