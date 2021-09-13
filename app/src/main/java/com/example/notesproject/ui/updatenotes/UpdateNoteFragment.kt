package com.example.notesproject.ui.updatenotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainApp
import com.example.notesproject.databinding.UpdateNoteFragmentBinding
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class UpdateNoteFragment : BaseFragment<UpdateNoteFragmentBinding, UpdateNoteViewModel>() {

	private val args: UpdateNoteFragmentArgs by navArgs()

	@Inject
	lateinit var updateNoteViewModelFactory: UpdateNoteViewModelFactory

	override val viewModel: UpdateNoteViewModel by viewModels {
		viewModelFactory()
	}

	override fun viewModelFactory(): ViewModelProvider.Factory = updateNoteViewModelFactory

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
			viewModel = this@UpdateNoteFragment.viewModel
			lifecycleOwner = viewLifecycleOwner
		}
	}


	private fun initObservers() {
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