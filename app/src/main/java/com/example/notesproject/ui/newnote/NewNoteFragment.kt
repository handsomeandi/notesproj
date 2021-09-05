package com.example.notesproject.ui.newnote

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.webkit.RenderProcessGoneDetail
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.notesproject.MainApp
import com.example.notesproject.databinding.NewNoteFragmentBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.BaseFragment
import com.example.notesproject.ui.ImagesAdapter
import javax.inject.Inject


class NewNoteFragment : BaseFragment<NewNoteFragmentBinding>() {

    @Inject
    lateinit var newNoteViewModel: NewNoteViewModel

    private val adapter = ImagesAdapter()

    private val imageResult = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
		newNoteViewModel.onImagesReceived(uris)
	}

    override fun viewBindingInflate(): NewNoteFragmentBinding =
        NewNoteFragmentBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainApp.instance.appComponent?.inject(this)
        initViews()
        initObservers()
    }

    private fun initViews() {
        with(binding) {
            viewModel = newNoteViewModel
            lifecycleOwner = viewLifecycleOwner
            rvImages.adapter = adapter
        }
    }

    private fun initObservers() {
        newNoteViewModel.currentEvent.observe(viewLifecycleOwner) {
            when (it) {
                NewNoteViewModel.Events.AddPressed -> {
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
                    Toast.makeText(requireContext(), "Too many images! Maximum: 10", Toast.LENGTH_SHORT).show()
                }
            }
        }
        newNoteViewModel.images.observe(viewLifecycleOwner){
            adapter.setItems(it)
        }
    }

}