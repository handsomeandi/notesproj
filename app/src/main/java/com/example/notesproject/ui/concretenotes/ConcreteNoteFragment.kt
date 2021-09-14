package com.example.notesproject.ui.concretenotes

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainApp
import com.example.notesproject.databinding.ConcreteNoteFragmentBinding
import com.example.notesproject.ui.base.BaseFragment
import com.example.notesproject.ui.ImagesAdapter
import com.example.notesproject.ui.OnImageClickListener
import javax.inject.Inject


class ConcreteNoteFragment : BaseFragment<ConcreteNoteFragmentBinding, ConcreteNoteViewModel>() {

	private val args: ConcreteNoteFragmentArgs by navArgs()

	@Inject
	lateinit var concreteNoteViewModelFactory: ConcreteNoteViewModelFactory


	override val viewModel: ConcreteNoteViewModel by viewModels {
		viewModelFactory()
	}

	override fun viewModelFactory(): ViewModelProvider.Factory = concreteNoteViewModelFactory

	private lateinit var adapter: ImagesAdapter

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
			viewModel = this@ConcreteNoteFragment.viewModel
			lifecycleOwner = viewLifecycleOwner
			adapter = ImagesAdapter(object : OnImageClickListener {
				override fun onImageClick(id: String) {
					Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
				}
			}, false)
			rvImages.adapter = adapter
		}
	}

	private fun initObserver() {
		viewModel.currentEvent.observe(viewLifecycleOwner) {
			when (it) {
				is ConcreteNoteViewModel.Events.DeletePressed -> {
					val alertDialog: AlertDialog? = activity?.let { activity ->
						val builder = AlertDialog.Builder(activity)
						builder.apply {
							setPositiveButton("Удалить") { dialog, id ->
								viewModel.onDelete()
							}
							setNegativeButton("Отмена") { dialog, id ->
								dialog.dismiss()
							}
						}
						builder.create()
					}
					alertDialog?.show()
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
				ConcreteNoteViewModel.Events.Deleted -> {
					findNavController().popBackStack()
				}
			}
		}
		viewModel.note.observe(viewLifecycleOwner) {
			adapter.setItems(it.images)
		}
	}
}