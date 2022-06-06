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

    private val videoAdapter: VideoAdapter by lazy {
        VideoAdapter(onVideoClick)
    }

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
            if (homeViewModel.list.isNotEmpty()) {
                homeViewModel.list[homeViewModel.mediaIndex].videoURL?.let { initializePlayer(it) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT <= 23 || homeViewModel.player == null)) {
            if (homeViewModel.list.isNotEmpty()) {
                homeViewModel.list[homeViewModel.mediaIndex].videoURL?.let { initializePlayer(it) }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private val onVideoClick = fun(video: VideoDescription, position: Int) {
        video.videoURL?.let { reStartPlayer(it) }
        homeViewModel.saveVideoToHistory(video)
        homeViewModel.mediaIndex = position
    }

    private fun reStartPlayer(url: String) {
        releasePlayer()
        initializePlayer(url)
    }


    private fun initializePlayer(videoUrl: String) {
        homeViewModel.player = ExoPlayer.Builder(requireContext())
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
        homeViewModel.player?.let { exoPlayer ->
            homeViewModel.playbackPosition = exoPlayer.currentPosition
            homeViewModel.currentItem = exoPlayer.currentMediaItemIndex
            homeViewModel.playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        homeViewModel.player = null
    }


    private fun setUpObserver() {
        homeViewModel.videoResult.observe(viewLifecycleOwner) {
            homeViewModel.list = it as List<VideoDescription>
            videoAdapter.addList(it)
            homeViewModel.list[0].videoURL?.let { it1 -> initializePlayer(it1) }
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