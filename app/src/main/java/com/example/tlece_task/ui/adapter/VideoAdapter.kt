package com.example.tlece_task.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.tlece_task.R
import com.example.tlece_task.base.BaseViewHolder
import com.example.tlece_task.databinding.ListItemVideoBinding
import com.example.tlece_task.model.VideoModelItem

class VideoAdapter : ListAdapter<VideoModelItem, BaseViewHolder>(DiffCallback) {

    private var clicked: ((type: VideoModelItem) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_video, parent, false)
    )

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ListItemVideoBinding.bind(view)

        override fun clear() {
            binding.tvTitle.text = ""
            binding.tvDescription.text = ""
        }

        override fun onBind(position: Int) {
            with(getItem(position)) {
                title?.let { binding.tvTitle.text = it }
                description?.let { binding.tvDescription.text = it }

                thumbnailUrl?.let { image ->
                    Glide.with(itemView.context).load(image).placeholder(R.drawable.sample_image)
                        .into(binding.imgThumb)
                }

                binding.imgThumb.setOnClickListener {
                    clicked?.invoke(this)
                }
            }
        }
    }

    fun clicked(clicked: (video: VideoModelItem) -> Unit) {
        this.clicked = clicked
    }

    private object DiffCallback : DiffUtil.ItemCallback<VideoModelItem>() {
        override fun areItemsTheSame(
            oldItem: VideoModelItem,
            newItem: VideoModelItem
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: VideoModelItem,
            newItem: VideoModelItem
        ): Boolean =
            oldItem == newItem
    }
}

