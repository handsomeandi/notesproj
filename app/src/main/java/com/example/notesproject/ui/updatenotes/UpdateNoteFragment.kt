package com.example.notesproject.ui.updatenotes

import android.os.Bundle
import android.view.View
import com.example.notesproject.MainApp
import com.example.notesproject.databinding.UpdateNoteFragmentBinding
import com.example.notesproject.ui.BaseFragment
import javax.inject.Inject


class UpdateNoteFragment : BaseFragment<UpdateNoteFragmentBinding>() {

    @Inject
    lateinit var viewModel: UpdateNoteViewModel

    override fun viewBindingInflate(): UpdateNoteFragmentBinding = UpdateNoteFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MainApp.instance.appComponent?.inject(this)
    }

}