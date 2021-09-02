package com.example.notesproject.ui.creatednotes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesproject.MainApp
import com.example.notesproject.clicker
import com.example.notesproject.databinding.CreatedNotesFragmentBinding
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class CreatedNotesFragment : BaseFragment<CreatedNotesFragmentBinding>() {

	@Inject
	lateinit var viewModel: CreatedNotesViewModel

	private lateinit var adapter: NotesRecyclerViewAdapter

	override fun viewBindingInflate() = CreatedNotesFragmentBinding.inflate(layoutInflater)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		MainApp.instance.appComponent?.inject(this)
		adapter = NotesRecyclerViewAdapter { id ->
			viewModel.onNotePressed(id)

		}
		with(binding) {
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
				CreatedNotesViewModel.Events.CreateNotePressed -> findNavController().navigate(
					CreatedNotesFragmentDirections.actionCreatedNotesFragmentToNewNoteFragment()
				)
			}

		}
	}
}

