package com.example.videoplayer.presentation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.R
import com.example.videoplayer.data.room.VideoHistory
import com.example.videoplayer.databinding.FragmentHistoryBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

enum class Sort(val type: String) {
    Views("views"), Recent("recent")
}

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!
    lateinit var historyViewModel: HistoryViewModel
    private val videoAdapter: VideoHistoryAdapter by lazy {
        VideoHistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        historyViewModel = requireActivity().getViewModel()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        initRecyclerView()
        setUpUI()
        handleSortButton()
    }


    private fun setUpUI() {
        if (historyViewModel.sort == Sort.Views)
            binding.sortBy.text = getString(R.string.sort_recent)
        else
            binding.sortBy.text = getString(R.string.sort_views)
        binding.swipeRefresh.setOnClickListener {
            historyViewModel.fetchVideosFromDB()
        }
    }

    private fun handleSortButton() {
        binding.sortBy.setOnClickListener {
            if (historyViewModel.sort == Sort.Views) {
                binding.sortBy.text = getString(R.string.sort_views)
                historyViewModel.sort = Sort.Recent
            } else if (historyViewModel.sort == Sort.Recent) {
                binding.sortBy.text = getString(R.string.sort_recent)
                historyViewModel.sort = Sort.Views
            }
            setUpObserver()
        }
    }

    private fun setUpObserver() {
        historyViewModel.videoDbResult.observe(viewLifecycleOwner) {
            addToList(it)
        }
    }

    private fun addToList(arrayList: ArrayList<VideoHistory>?) {
        if (historyViewModel.sort == Sort.Views) {
            arrayList?.sortByDescending { it -> it.views }
        } else if (historyViewModel.sort == Sort.Recent) {
            arrayList?.sortByDescending { it -> it.lastViewed }
        }
        historyViewModel.list = arrayList as List<VideoHistory>
        videoAdapter.addList(historyViewModel.list)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        _binding?.rvVideoListHistory?.apply {
            layoutManager = linearLayoutManager
            adapter = videoAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}