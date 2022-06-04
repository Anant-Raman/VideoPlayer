package com.example.videoplayer.presentation.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoplayer.R
import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.databinding.ItemVideoBinding
import com.example.videoplayer.databinding.ItemVideoBinding.inflate

class VideoAdapter(
    val onVideoClicked: (videoUrl: String) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var selectedPosition = RecyclerView.NO_POSITION
    private val videoList: MutableList<VideoDescription?> = mutableListOf()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoAdapter.ViewHolder).bind(videoList[position])

        holder.itemView.isSelected = selectedPosition == position

//        if(position == selectedPosition) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.itemView.setBackgroundColor(context.getColor(R.color.purple_200))
//            }
//        }else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.itemView.setBackgroundColor(context.getColor(R.color.white))
//            }
//        }
    }

    override fun getItemCount(): Int = videoList.size

    inner class ViewHolder(private val viewBinding: ItemVideoBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(video: VideoDescription?) {
            viewBinding.tvVideoName.text = video?.title
            viewBinding.tvVideoDescription.text = video?.description
            Glide.with(context)
                .load(video?.thumbnailURL)
                .into(viewBinding.ivCategoryIcon)
            viewBinding.rootView.setOnClickListener {
                video?.videoURL?.let { it1 -> onVideoClicked(it1) }
                notifyItemChanged(selectedPosition)
                selectedPosition = layoutPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    fun addList(list: List<VideoDescription?>) {
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