package com.example.notesproject.ui.newnote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.example.notesproject.MainApp
import com.example.notesproject.Util.IMAGE_DIRECTORY
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.databinding.NewNoteFragmentBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.subscribeIoObserveMain
import com.example.notesproject.toImageObjects
import com.example.notesproject.ui.BaseFragment
import com.example.notesproject.ui.ImagesAdapter
import com.example.notesproject.ui.OnImageClickListener
import io.reactivex.rxjava3.core.Completable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class NewNoteFragment : BaseFragment<NewNoteFragmentBinding>() {

	@Inject
	lateinit var newNoteViewModel: NewNoteViewModel

	private lateinit var adapter: ImagesAdapter

	private lateinit var  directory: File

	private val imageResult = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
		newNoteViewModel.onImagesReceived(uris.toImageObjects())
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
			directory = requireActivity().applicationContext.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

			adapter = ImagesAdapter(object : OnImageClickListener {
				override fun onImageClick(id: String) {
					Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
				}

				override fun onDeleteClick(id: String) {
//					Toast.makeText(requireContext(), "Delete clicked $id", Toast.LENGTH_SHORT).show()
					newNoteViewModel.onRemoveImagePressed(id)
				}
			}, true)
			rvImages.adapter = adapter
		}
	}

	private fun initObservers() {
		newNoteViewModel.currentEvent.observe(viewLifecycleOwner) {
			when (it) {
				is NewNoteViewModel.Events.AddPressed -> {
					saveImages(it.images).subscribeIoObserveMain({
						findNavController().popBackStack()
					}, { e ->
						Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
					})
//					findNavController().popBackStack()
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
		newNoteViewModel.images.observe(viewLifecycleOwner) {
			adapter.setItems(it)
		}
	}

	private fun saveImages(images: List<ImageObject>?): Completable {
		return Completable.create {
			try {
				images?.forEach { image ->
					val path = File(directory, "${image.id}.png")

					var fos: FileOutputStream? = null
					fos = FileOutputStream(path)
					BitmapFactory.decodeStream(
						requireActivity().contentResolver.openInputStream(image.uri.toUri()),
						null,
						null
					)
						?.compress(Bitmap.CompressFormat.PNG, 100, fos)
					try {
						fos?.close()
					} catch (e: IOException) {
						e.printStackTrace()
					}
				}
				it.onComplete()
			} catch (e: Exception) {
				it.onError(e)
			}
		}
	}

}