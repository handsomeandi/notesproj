package com.example.notesproject.ui.newnote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.notesproject.MainApp
import com.example.notesproject.databinding.NewNoteFragmentBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class NewNoteFragment : BaseFragment<NewNoteFragmentBinding>() {

    @Inject
    lateinit var newNoteViewModel: NewNoteViewModel

    private val imageResult = registerForActivityResult(object:ActivityResultContracts.GetContent(){
        override fun createIntent(context: Context, input: String): Intent {
            return super.createIntent(context, input).apply {
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
        }
    }) { uri ->
		binding.ivAddImageAction.setImageURI(uri)
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
            }
        }
    }

}