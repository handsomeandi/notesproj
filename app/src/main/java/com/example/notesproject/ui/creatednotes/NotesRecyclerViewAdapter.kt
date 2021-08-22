package com.example.notesproject.ui.creatednotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.R
import com.example.notesproject.data.model.Note
import com.example.notesproject.databinding.NoteItemBinding

class NotesRecyclerViewAdapter constructor(private val noteClickListener: OnNoteClickListener) :
    RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesHolder>() {
    inner class NotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var note: Note? = null
        private val binding = DataBindingUtil.bind<NoteItemBinding>(itemView)
        fun setData(note: Note) {
            this.note = note
            binding?.tvCardNoteTitle?.text = this.note?.title
            bind(note)
        }

        fun bind(note: Note) {
            binding?.noteCard = note
            binding?.onNoteClickListener = noteClickListener
        }
    }

    private var containerList: ArrayList<Note>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesHolder(view)
    }

    override fun getItemCount(): Int {
        return containerList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        containerList?.let {
            holder.setData(it[position])
        }
    }

    fun addItem(note: Note) {
        containerList?.let {
            val index = it.size
            it.add(note)
            notifyItemInserted(index)
            notifyItemRangeChanged(index, it.size)
        }
    }

    fun setItems(notes: ArrayList<Note>) {
        containerList = (ArrayList(notes))
        notifyDataSetChanged()
    }
}

fun interface OnNoteClickListener {
    fun onClick(id: Int)
}