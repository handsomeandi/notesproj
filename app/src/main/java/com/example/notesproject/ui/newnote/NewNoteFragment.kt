package com.example.notesproject.ui.newnote

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers.io
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class NewNoteFragment : BaseFragment<NewNoteFragmentBinding>() {

    private val newNoteViewModel: NewNoteViewModel by viewModels()

    private lateinit var adapter: ImagesAdapter

    private val imageResult =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            newNoteViewModel.onImagesReceived(uris.toImageObjects())
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
            viewModel = newNoteViewModel
            lifecycleOwner = viewLifecycleOwner

            adapter = ImagesAdapter(object : OnImageClickListener {
                override fun onImageClick(id: String) {
                    Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(id: String) {
                    newNoteViewModel.onRemoveImagePressed(id)
                }
            }, true)
            rvImages.adapter = adapter
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                newNoteViewModel.onBackPressed()
            }
        }
    }

    private fun initObservers() {
        newNoteViewModel.currentEvent.observe(viewLifecycleOwner) {
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
                    deleteImages(it.images).subscribeIoObserveMain(
                        { findNavController().popBackStack() },
                        {
                            logErrorMessage(it.message)
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
        newNoteViewModel.images.observe(viewLifecycleOwner) { images ->
            if (images.isNotEmpty()) {
                saveImages(images.filter {
                    !File(
                        IMAGE_DIRECTORY,
                        "${it.id}.png"
                    ).exists()
                }).subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
//					.doOnNext {
//						adapter.addItem(it)
//					}
                    .doOnComplete {
                        adapter.setItems(images)
                    }
                    .subscribe(
                        { Log.d("testing", "success saving") },
                        { logErrorMessage(it.message) }
                    )

            }
        }
    }

    private fun saveImages(images: List<ImageObject>?): Observable<ImageObject> {
        return Observable.create { mainEmitter ->
            try {
                if (images?.isNotEmpty() == true) {
                    images.forEach { image ->
                        Single.create<ImageObject> {
                            val path = File(IMAGE_DIRECTORY, "${image.id}.png")

                            val fos = FileOutputStream(path)
                            val isDecoded = BitmapFactory.decodeStream(
                                requireActivity().contentResolver.openInputStream(image.uri.toUri()),
                                null,
                                null
                            )?.compress(Bitmap.CompressFormat.PNG, 100, fos)
                            try {
                                fos.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                                it.onError(e)
                            }
                            isDecoded?.let { isSuccessDecoded ->
                                if (isSuccessDecoded) it.onSuccess(image)
                            }
                        }.subscribeOn(io()).subscribe(
                            {
                                mainEmitter.onNext(it)
                                if (it == images.last()) {
                                    mainEmitter.onComplete()
                                }
                            },
                            { logErrorMessage(it.message) }
                        )
                    }
                } else {
                    mainEmitter.onComplete()
                }
            } catch (e: Exception) {
                mainEmitter.onError(e)
            }
        }
    }


    private fun deleteImages(images: List<ImageObject>?): Completable {
        return Completable.create {
            try {
                images?.forEach { image ->
                    val fileToDelete = File(IMAGE_DIRECTORY, "${image.id}.png")
                    if (fileToDelete.exists()) {
                        if (fileToDelete.delete()) {
                            Log.d("testing", "file Deleted :")
                        } else {
                            Log.d("testing", "file not Deleted :")
                        }
                    }
                }
                it.onComplete()

            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}