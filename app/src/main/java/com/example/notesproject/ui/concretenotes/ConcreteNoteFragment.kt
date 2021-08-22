package com.example.notesproject.ui.concretenotes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.notesproject.Util
import com.example.notesproject.databinding.ConcreteNoteFragmentBinding
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class ConcreteNoteFragment : BaseFragment<ConcreteNoteFragmentBinding>() {

    private val args: ConcreteNoteFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: ConcreteNoteViewModel

    override fun viewBindingInflate(): ConcreteNoteFragmentBinding =
        ConcreteNoteFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = Util.sampleData().find { it.id == args.id }
        with(binding) {
            note?.let {
                tvNoteTitle.text = it.title
                tvNoteBody.text = it.noteText
            }
        }
    }
}