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
    lateinit var updateNoteViewModel: UpdateNoteViewModel

    override fun viewBindingInflate(): UpdateNoteFragmentBinding =
        UpdateNoteFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MainApp.instance.appComponent?.inject(this)
        updateNoteViewModel.onCreate(args.id)
        initViews()
        initObservers()
    }

    private fun initViews() {
        with(binding) {
            viewModel = updateNoteViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }


    private fun initObservers() {
        updateNoteViewModel.currentEvent.observe(viewLifecycleOwner) {
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