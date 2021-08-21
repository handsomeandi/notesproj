package com.example.notesproject.ui.creatednotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.MainApp
import com.example.notesproject.R
import com.example.notesproject.Util.setStatusBarColor
import com.example.notesproject.data.di.AppComponent
import kotlinx.coroutines.MainScope
import javax.inject.Inject


class CreatedNotesFragment : Fragment() {

    companion object {
        fun newInstance() = CreatedNotesFragment()
    }

    @Inject
    lateinit var viewModel: CreatedNotesViewModel

    private lateinit var notesRecyclerView: RecyclerView

    private lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.created_notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MainApp.instance.appComponent?.inject(this)
        adapter = NotesRecyclerViewAdapter { id ->
            findNavController().run {
                this.navigate(CreatedNotesFragmentDirections.actionCreatedNotesFragmentToConcreteNoteFragment(id))
            }
        }
        notesRecyclerView = view.findViewById(R.id.notesRecyclerView)
        notesRecyclerView.let {
            it.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            it.adapter = adapter
        }
        viewModel.onCreate()
        initObserver()
    }


    private fun initObserver() {
        viewModel.notes.observe(viewLifecycleOwner, { notes ->
            adapter.setItems(notes)
        })
    }

    override fun onResume() {
        super.onResume()
        activity?.setStatusBarColor(R.color.white, false)
    }
}

