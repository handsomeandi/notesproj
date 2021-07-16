package com.example.notesproject.creatednotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.db.Note
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
        adapter = NotesRecyclerViewAdapter()
        adapter.onClick = {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
        }

        notesRecyclerView = view.findViewById(R.id.notesRecyclerView)
        notesRecyclerView.let{
            it.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            it.adapter = adapter
        }

        adapter.setItems(sampleData())

        viewModel.some_fun()
    }

    fun sampleData() : ArrayList<Note>{
        return arrayListOf(
            Note(1,"Title1", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(2,"Title2", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(3,"Title3", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(4,"Title4", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(5,"Title5", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none"),
            Note(6,"Title6", "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ", "30.05.2002", "15.07.2002", "none")
        )



    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }

}