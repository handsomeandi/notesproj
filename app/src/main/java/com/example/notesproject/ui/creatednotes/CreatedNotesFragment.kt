package com.example.notesproject.ui.creatednotes

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesproject.MainApp
import com.example.notesproject.databinding.CreatedNotesFragmentBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.ui.base.BaseFragment
import javax.inject.Inject


class CreatedNotesFragment : BaseFragment<CreatedNotesFragmentBinding, CreatedNotesViewModel>() {

	@Inject
	lateinit var createdNotesViewModelFactory: CreatedNoteViewModelFactory

	override val viewModel: CreatedNotesViewModel by viewModels {
		viewModelFactory()
	}

	override fun viewModelFactory(): ViewModelProvider.Factory = createdNotesViewModelFactory

	private lateinit var adapter: NotesRecyclerViewAdapter

	override fun viewBindingInflate() = CreatedNotesFragmentBinding.inflate(layoutInflater)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		MainApp.instance.appComponent?.inject(this)
		adapter = NotesRecyclerViewAdapter(requireActivity() as AppCompatActivity,
			{ id -> viewModel.onNotePressed(id) },
			{ notes -> viewModel.onDeletePressed(notes) })
		with(binding)
		{
			rvNotes.apply {
				layoutManager =
					LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
				adapter = this@CreatedNotesFragment.adapter

			}
			createdNotesViewModel = viewModel
			lifecycleOwner = viewLifecycleOwner
		}
		viewModel.onCreate()
		initObserver()
	}

	private fun initObserver() {
		viewModel.notes.observe(viewLifecycleOwner, { notes ->
			adapter.setItems(notes)
		})
		viewModel.currentEvent.observe(viewLifecycleOwner) {
			when (it) {
				CreatedNotesViewModel.Events.Initial -> {

				}
				is CreatedNotesViewModel.Events.NotePressed -> findNavController().navigate(
					CreatedNotesFragmentDirections.actionCreatedNotesFragmentToConcreteNoteFragment(
						it.id
					)
				)
				CreatedNotesViewModel.Events.CreateNotePressed -> {
					try {
						findNavController().navigate(
							CreatedNotesFragmentDirections.actionCreatedNotesFragmentToNewNoteFragment()
						)
					} catch (e: Exception) {
						logErrorMessage(e.message)
					}
				}
				is CreatedNotesViewModel.Events.DeletePressed -> {
					val notes = it.notesList.toMutableList()
					val alertDialog: AlertDialog? = AlertDialog.Builder(this.context).apply {
						setPositiveButton("??????????????") { dialog, id ->
							viewModel.onDelete(notes)
						}
						setNegativeButton("????????????") { dialog, id ->
							dialog.dismiss()
						}
					}.create()
					alertDialog?.show()
				}
				CreatedNotesViewModel.Events.Deleted -> {

				}
			}

		}
	}

	override fun onStop() {
		super.onStop()
		adapter.actionMode?.finish()
	}
}

