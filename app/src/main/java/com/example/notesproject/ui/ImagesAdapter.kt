package com.example.notesproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notesproject.R
import com.example.notesproject.Util.IMAGE_DIRECTORY
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.databinding.ItemImageBinding
import java.io.File


class ImagesAdapter constructor(private val imageClickListener: OnImageClickListener, private val isEditable: Boolean) :
	RecyclerView.Adapter<ImagesAdapter.ImagesHolder>() {

	private var containerList: ArrayList<ImageObject>? = arrayListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
		return ImagesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
	}

	override fun onBindViewHolder(holder: ImagesHolder, position: Int) {
		containerList?.let {
			holder.setData(it[position])
		}
	}

	override fun getItemCount(): Int {
		return containerList?.size ?: 0
	}

	inner class ImagesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = DataBindingUtil.bind<ItemImageBinding>(itemView)
		fun setData(imageObject: ImageObject) {
			binding?.ivImage?.let { iv ->
				Glide.with(itemView.context).load(
					File(
						IMAGE_DIRECTORY, "${imageObject.id}.png"
					)
				).into(iv)
			}
			binding?.ivDelete?.isVisible = isEditable
			bindImage(imageObject)
		}

		private fun bindImage(image: ImageObject) {
			binding?.image = image
			binding?.imageClickListener = imageClickListener
		}
	}


	fun addItem(image: ImageObject) {
		containerList?.let {
			val index = it.size
			it.add(image)
			notifyItemInserted(index)
			notifyItemRangeChanged(index, it.size)
		}
	}

	fun setItems(images: List<ImageObject>) {
		containerList = (ArrayList(images))
		notifyDataSetChanged()
	}
}

interface OnImageClickListener {
	fun onImageClick(id: String)
	fun onDeleteClick(id: String) {}
}