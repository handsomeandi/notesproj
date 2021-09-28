package com.example.notesproject.ui.creatednotes

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.R
import com.example.notesproject.databinding.NoteItemBinding
import com.example.notesproject.domain.model.NoteModel

class NotesRecyclerViewAdapter constructor(
	private val activity: AppCompatActivity,
	private val noteClickListener: OnNoteClickListener,
	private val actionButtonClickListener: OnActionButtonClickListener,
) :
	RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesHolder>(), ActionMode.Callback {

	private var multiSelect = false
		set(value) {
			field = value

		}
	private val selectedItems = arrayListOf<NoteModel>()

	private var actionMode: ActionMode? = null

	inner class NotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private var note: NoteModel? = null
		private val binding = DataBindingUtil.bind<NoteItemBinding>(itemView)
		fun setData(note: NoteModel) {
			this.note = note
			binding?.tvCardNoteTitle?.text = this.note?.title
			bind(note)
		}

		fun bind(note: NoteModel) {
			binding?.noteCard = note
		}
	}

	private var notesList: ArrayList<NoteModel>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
		return NotesHolder(view)
	}

	override fun getItemCount(): Int {
		return notesList?.size ?: 0
	}

	override fun onBindViewHolder(holder: NotesHolder, position: Int) {
		notesList?.let {
			holder.setData(it[position])
		}
		val currentNote = notesList?.get(position)
		if (selectedItems.contains(currentNote)) {
			holder.itemView.alpha = 0.3f
		} else {
			holder.itemView.alpha = 1.0f
		}

		holder.itemView.setOnLongClickListener {
			if (!multiSelect) {
				multiSelect = true
				activity.startSupportActionMode(this)
				currentNote?.let { selectItem(holder, it) }
				true
			} else {
				false
			}
		}

		holder.itemView.setOnClickListener {
			if (multiSelect)
				currentNote?.let { selectItem(holder, it) }
			else
				currentNote?.id?.let {
					noteClickListener.onClick(it)
				}
		}
	}

	private fun selectItem(holder: NotesHolder, note: NoteModel) {
		if (selectedItems.contains(note)) {
			selectedItems.remove(note)
			holder.itemView.alpha = 1.0f
		} else {
			selectedItems.add(note)
			holder.itemView.alpha = 0.3f
		}
		multiSelect = selectedItems.isNotEmpty()
		if (!multiSelect) actionMode?.finish() else actionMode?.title = "Выбрано: ${selectedItems.size}"
	}

	fun addItem(note: NoteModel) {
		notesList?.let {
			val index = it.size
			it.add(note)
			notifyItemInserted(index)
			notifyItemRangeChanged(index, it.size)
		}
	}

	fun setItems(notes: List<NoteModel>) {
		notesList = (ArrayList(notes))
		notifyDataSetChanged()
	}

	override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
		actionMode = mode
		mode?.let {
			val inflater: MenuInflater = it.menuInflater
			inflater.inflate(R.menu.menu_selection, menu)
		}
		return true
	}

	override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
		return true
	}

	override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
		if (item?.itemId == R.id.action_delete) {
			Log.d("testing", "Selected images deleted")
			actionButtonClickListener.onClick(selectedItems)
			mode?.finish()
		}
		return true
	}

	override fun onDestroyActionMode(mode: ActionMode?) {
		multiSelect = false
		selectedItems.clear()
		notifyDataSetChanged()
	}
}

fun interface OnNoteClickListener {
	fun onClick(id: Int)
}

fun interface OnActionButtonClickListener {
	fun onClick(notes: List<NoteModel>)
}