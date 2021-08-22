package com.example.notesproject.ui.concretenotes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainApp
import com.example.notesproject.clicker
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
        MainApp.instance.appComponent?.inject(this)
        viewModel.onCreate(args.id)
        initView()
        initObserver()
    }

    private fun initView() {
        with(binding) {
            btnDelete clicker { viewModel.onDeletePressed() }
            btnUpdate clicker { viewModel.onUpdatePressed() }
        }
    }

    private fun initObserver() {
        viewModel.note.observe(viewLifecycleOwner) {
            with(binding) {
                tvNoteTitle.text = it.title
                tvNoteBody.text = it.noteText
            }
        }
        viewModel.currentEvent.observe(viewLifecycleOwner) {
            when (it) {
                is ConcreteNoteViewModel.Events.DeletePressed -> {
                    //show dialog and then delete
                }
                ConcreteNoteViewModel.Events.Initial -> {

                }
                is ConcreteNoteViewModel.Events.UpdatePressed -> {
                    findNavController().navigate(
                        ConcreteNoteFragmentDirections.actionConcreteNoteFragmentToUpdateNoteFragment(
                            args.id
                        )
                    )
                }
            }
        }
    }
}