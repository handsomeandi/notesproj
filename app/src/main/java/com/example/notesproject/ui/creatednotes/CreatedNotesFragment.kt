package com.example.notesproject.ui.creatednotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatedNotesFragment : Fragment() {

    companion object {
        fun newInstance() = CreatedNotesFragment()
    }

    private val viewModel: CreatedNotesViewModel by viewModels()

    private lateinit var notesRecyclerView: RecyclerView

    private lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.created_notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NotesRecyclerViewAdapter{ id ->
            Toast.makeText(context,id,Toast.LENGTH_SHORT).show()
        }
//        adapter.onClick = {
//            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
//        }

        notesRecyclerView = view.findViewById(R.id.notesRecyclerView)
        notesRecyclerView.let{
            it.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            it.adapter = adapter
        }

        initObserver()

    }

    private fun initObserver() {
        viewModel.notes.observe(viewLifecycleOwner, { notes ->
            adapter.setItems(notes)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPhotos()
    }





}

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }

