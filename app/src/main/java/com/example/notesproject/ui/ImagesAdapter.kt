package com.example.notesproject.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.R
import com.example.notesproject.databinding.ItemImageBinding

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImagesHolder>() {

    private var containerList: ArrayList<Uri>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
        return ImagesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false))
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
        private var imgUri: Uri? = null
        private val binding = DataBindingUtil.bind<ItemImageBinding>(itemView)
        fun setData(uri: Uri) {
            this.imgUri = uri
            binding?.ivImage?.setImageURI(uri)
        }
    }


    fun addItem(uri: Uri) {
        containerList?.let {
            val index = it.size
            it.add(uri)
            notifyItemInserted(index)
            notifyItemRangeChanged(index, it.size)
        }
    }

    fun setItems(uris: List<Uri>) {
        containerList = (ArrayList(uris))
        notifyDataSetChanged()
    }

}