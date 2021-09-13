package com.example.notesproject.ui.newnote

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesproject.MainApp
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.databinding.NewNoteFragmentBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.toImageObjects
import com.example.notesproject.ui.BaseFragment
import com.example.notesproject.ui.ImagesAdapter
import com.example.notesproject.ui.OnImageClickListener
import javax.inject.Inject


class NewNoteFragment : BaseFragment<NewNoteFragmentBinding, NewNoteViewModel>() {

	@Inject
	lateinit var newNoteViewModelFactory: NewNoteViewModelFactory

	override fun viewModelFactory(): ViewModelProvider.Factory = newNoteViewModelFactory

	override val viewModel: NewNoteViewModel by viewModels { viewModelFactory() }

	private lateinit var adapter: ImagesAdapter

	private val imageResult =
		registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
			viewModel.onImagesReceived(uris.toImageObjects(), requireActivity().contentResolver)
		}

	override fun viewBindingInflate(): NewNoteFragmentBinding =
		NewNoteFragmentBinding.inflate(layoutInflater)

	override fun onCreate(savedInstanceState: Bundle?) {
		MainApp.instance.appComponent?.inject(this)
		super.onCreate(savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews()
		initObservers()
	}

	private fun initViews() {
		with(binding) {
			viewModel = this@NewNoteFragment.viewModel
			lifecycleOwner = viewLifecycleOwner

			adapter = ImagesAdapter(object : OnImageClickListener {
				override fun onImageClick(id: String) {
					Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
				}

				override fun onDeleteClick(image: ImageObject) {
					this@NewNoteFragment.viewModel.onRemoveImagePressed(image)
				}
			}, true)
			rvImages.adapter = adapter
			requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
				this@NewNoteFragment.viewModel.onBackPressed()
			}
		}
	}

	private fun initObservers() {
		viewModel.currentEvent.observe(viewLifecycleOwner) {
			when (it) {
				is NewNoteViewModel.Events.AddPressed -> {
					findNavController().popBackStack()
				}
				NewNoteViewModel.Events.Initial -> {
				}
				NewNoteViewModel.Events.ChooseImagePressed -> {
					try {
						imageResult.launch("image/*")
					} catch (e: Exception) {
						logErrorMessage(e.message)
					}
				}
				NewNoteViewModel.Events.ImagesReceived -> {
//                    binding.ivAddImageAction.visibility = View.GONE
				}
				NewNoteViewModel.Events.ImagesLimit -> {
					Toast.makeText(
						requireContext(),
						"Too many images! Maximum: 10",
						Toast.LENGTH_SHORT
					).show()
				}

				is NewNoteViewModel.Events.BackPressed -> {
					if(findNavController().popBackStack()){
						logErrorMessage("couldn't navigate")
					}
				}
			}
		}
		viewModel.images.observe(viewLifecycleOwner) { images ->
			adapter.setItems(images)
		}
	}


}