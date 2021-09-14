package com.example.notesproject.ui.updatenotes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainApp
import com.example.notesproject.clicker
import com.example.notesproject.data.NoteMapper.toImageObjects
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.databinding.UpdateNoteFragmentBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.base.BaseFragment
import com.example.notesproject.ui.ImagesAdapter
import com.example.notesproject.ui.OnImageClickListener
import javax.inject.Inject


class UpdateNoteFragment : BaseFragment<UpdateNoteFragmentBinding, UpdateNoteViewModel>() {

	private val args: UpdateNoteFragmentArgs by navArgs()

	@Inject
	lateinit var updateNoteViewModelFactory: UpdateNoteViewModelFactory

	override val viewModel: UpdateNoteViewModel by viewModels {
		viewModelFactory()
	}

	override fun viewModelFactory(): ViewModelProvider.Factory = updateNoteViewModelFactory

	private lateinit var adapter: ImagesAdapter

	private val imageResult =
		registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
			viewModel.onImagesReceived(uris.toImageObjects(), requireActivity().contentResolver)
		}

	override fun viewBindingInflate(): UpdateNoteFragmentBinding =
		UpdateNoteFragmentBinding.inflate(layoutInflater)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		MainApp.instance.appComponent?.inject(this)
		viewModel.onCreate(args.id)
		initViews()
		initObservers()
	}

	private fun initViews() {
		adapter = ImagesAdapter(object : OnImageClickListener {
			override fun onImageClick(id: String) {
				Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
			}

			override fun onDeleteClick(image: ImageObject) {
				viewModel.onRemoveImagePressed(image)
			}
		}, true)
		requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
			viewModel.onCancel()
		}
		with(binding) {
			viewModel = this@UpdateNoteFragment.viewModel
			lifecycleOwner = viewLifecycleOwner
			rvImages.adapter = adapter
			btnCancel clicker this@UpdateNoteFragment.viewModel::onCancel
			btnSave clicker { this@UpdateNoteFragment.viewModel.onSavePressed(requireActivity().contentResolver) }
			ivAddImageAction clicker this@UpdateNoteFragment.viewModel::onSelectImagePressed
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
				UpdateNoteViewModel.Events.ImagesLimit -> {
					Toast.makeText(
						requireContext(),
						"Too many images! Maximum: 10",
						Toast.LENGTH_SHORT
					).show()
				}
				UpdateNoteViewModel.Events.ImagesReceived -> {

				}
				UpdateNoteViewModel.Events.ChooseImagePressed -> {
					try {
						imageResult.launch("image/*")
					} catch (e: Exception) {
						logErrorMessage(e.message)
					}
				}
			}
		}
		viewModel.images.observe(viewLifecycleOwner) {
			adapter.setItems(it)
		}
	}

}