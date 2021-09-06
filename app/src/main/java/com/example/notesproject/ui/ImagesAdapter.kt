package com.example.notesproject.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.R
import com.example.notesproject.Util.IMAGE_DIRECTORY
import com.example.notesproject.data.model.ImageObject
import com.example.notesproject.databinding.ItemImageBinding
import com.example.notesproject.logErrorMessage
import com.example.notesproject.subscribeIoObserveMain
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class ImagesAdapter constructor(private val imageClickListener: OnImageClickListener, private val isEditable: Boolean) :
	RecyclerView.Adapter<ImagesAdapter.ImagesHolder>() {

	private var containerList: ArrayList<ImageObject>? = null

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
			try {
				binding?.ivImage?.setImageURI(imageObject.uri.toUri())
			} catch (e: Exception) {
				try {
					getImage(itemView.context, imageObject.id).subscribeIoObserveMain(
						{
							val sheesh = it
							binding?.ivImage?.setImageBitmap(it) },
						{ logErrorMessage(it.message) }
					)
				} catch (e: FileNotFoundException) {
					e.printStackTrace()
				}
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

	fun getImage(context: Context, imageName: String): Single<Bitmap> {
		return Single.create { emitter ->
			try {
				BitmapFactory.decodeStream(
					FileInputStream(
						File(
							context.getDir(
								IMAGE_DIRECTORY,
								Context.MODE_PRIVATE
							), "${imageName}.png"
						)
					)
				).also {
					emitter.onSuccess(it)
				}
			} catch (e: Exception) {
				logErrorMessage(e.message)
			}
		}
	}

}

interface OnImageClickListener {
	fun onImageClick(id: String)
	fun onDeleteClick(id: String) {}
}