package com.example.notesproject.ui.concretenotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.notesproject.R


class ConcreteNoteFragment : Fragment() {

    private val args: ConcreteNoteFragmentArgs by navArgs()


    //TODO: inject
    private lateinit var viewModel: ConcreteNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.concrete_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context,args.id.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = ConcreteNoteFragment()
    }
}