package com.example.videoplayer.presentation.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoplayer.data.room.VideoHistory
import com.example.videoplayer.databinding.ItemVideoHistoryBinding

class VideoHistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var selectedPosition = RecyclerView.NO_POSITION
    private val videoList: MutableList<VideoHistory?> = mutableListOf()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemVideoHistoryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHistoryAdapter.ViewHolder).bind(videoList[position])

        holder.itemView.isSelected = selectedPosition == position
    }

    override fun getItemCount(): Int = videoList.size

    inner class ViewHolder(private val viewBinding: ItemVideoHistoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(video: VideoHistory?) {
            viewBinding.tvVideoName.text = video?.videoDescription?.title
            viewBinding.tvVideoDescription.text = video?.videoDescription?.description
            "${video?.views.toString()} views".also { viewBinding.tvVideoView.text = it }
            Glide.with(context)
                .load(video?.videoDescription?.thumbnailURL)
                .into(viewBinding.ivCategoryIcon)
        }
    }

    fun addList(list: List<VideoHistory?>) {
        if (list.isNotEmpty()) {
            val newIndex = videoList.size
            val newItemsCount = list.size
            if (videoList.addAll(list)) notifyItemRangeInserted(newIndex, newItemsCount)
        }
        videoList.clear()
        videoList.addAll(list)
        notifyDataSetChanged()
    }
}