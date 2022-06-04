package com.example.videoplayer.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.databinding.FragmentHomeBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var homeViewModel: HomeViewModel


    private var list: List<VideoDescription> = emptyList()
    private var player: ExoPlayer? = null
    private val videoAdapter: VideoAdapter by lazy {
        VideoAdapter(onCategoryDeleteClick)
    }

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = requireActivity().getViewModel()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        initRecyclerView()
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            if (list.isNotEmpty()) {
                list[0].videoURL?.let { initializePlayer(it) }
            }
        }
    }

    public override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            if (list.isNotEmpty()) {
                list[0].videoURL?.let { initializePlayer(it) }
            }
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private val onCategoryDeleteClick = fun(videoUrl: String) {
        reStartPlayer(videoUrl)
    }

    private fun reStartPlayer(url: String) {
        releasePlayer()
        initializePlayer(url)
    }


    private fun initializePlayer(videoUrl: String) {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(videoUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    private fun setupUI() {

    }

    private fun setUpObserver() {
        homeViewModel.videoResult.observe(viewLifecycleOwner) {
            list = it as List<VideoDescription>
            videoAdapter.addList(it)
            list[0].videoURL?.let { it1 -> initializePlayer(it1) }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        _binding?.rvVideoList?.apply {
            layoutManager = linearLayoutManager
            adapter = videoAdapter
        }
    }

    // onDestroyView.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}