package com.example.notesproject.ui.updatenotes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainApp
import com.example.notesproject.clicker
import com.example.notesproject.data.model.Note
import com.example.notesproject.databinding.UpdateNoteFragmentBinding
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class UpdateNoteFragment : BaseFragment<UpdateNoteFragmentBinding>() {

    private val args: UpdateNoteFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: UpdateNoteViewModel

    override fun viewBindingInflate(): UpdateNoteFragmentBinding =
        UpdateNoteFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MainApp.instance.appComponent?.inject(this)
        viewModel.onCreate(args.id)
        initViews()
        initObservers()
    }

    private fun initViews() {
        with(binding) {
            btnSave clicker {
                viewModel.onSavePressed(
                    etNoteTitle.text.toString(),
                    etNoteBody.text.toString()
                )
            }
            btnCancel clicker {
                viewModel.onCancelPressed()
            }
        }
    }


    private fun initObservers() {
        viewModel.note.observe(viewLifecycleOwner) {
            with(binding) {
                etNoteTitle.setText(it.title)
                etNoteBody.setText(it.noteText)
            }
        }
        viewModel.currentEvent.observe(viewLifecycleOwner) {
            when (it) {
                UpdateNoteViewModel.Events.Initial -> {
                }
                UpdateNoteViewModel.Events.SavePressed, UpdateNoteViewModel.Events.CancelPressed -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

}