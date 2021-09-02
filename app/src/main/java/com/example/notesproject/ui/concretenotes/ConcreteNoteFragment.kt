package com.example.notesproject.ui.concretenotes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesproject.MainApp
import com.example.notesproject.R
import com.example.notesproject.clicker
import com.example.notesproject.databinding.ConcreteNoteFragmentBinding
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class ConcreteNoteFragment : BaseFragment<ConcreteNoteFragmentBinding>() {

	private val args: ConcreteNoteFragmentArgs by navArgs()

	@Inject
	lateinit var concreteNoteViewModel: ConcreteNoteViewModel

	override fun viewBindingInflate(): ConcreteNoteFragmentBinding =
		ConcreteNoteFragmentBinding.inflate(layoutInflater)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		MainApp.instance.appComponent?.inject(this)
		concreteNoteViewModel.onCreate(args.id)
		initView()
		initObserver()
	}

	private fun initView() {
		with(binding) {
			viewModel = concreteNoteViewModel
			lifecycleOwner = viewLifecycleOwner
		}
	}

	private fun initObserver() {
		concreteNoteViewModel.currentEvent.observe(viewLifecycleOwner) {
			when (it) {
				is ConcreteNoteViewModel.Events.DeletePressed -> {
					val alertDialog: AlertDialog? = activity?.let {
						val builder = AlertDialog.Builder(it)
						builder.apply {
							setPositiveButton("Удалить") { dialog, id ->
								concreteNoteViewModel.onDelete()
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
	}
}