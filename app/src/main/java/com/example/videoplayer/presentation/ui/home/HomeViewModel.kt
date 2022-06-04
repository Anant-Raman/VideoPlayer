package com.example.videoplayer.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.common.helper.NetworkHelper
import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.data.repository.VideoRepository
import com.example.videoplayer.domain.AppResult
import kotlinx.coroutines.launch

class HomeViewModel(
    private val videoRepository: VideoRepository,
                    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _videoResult = MutableLiveData<ArrayList<VideoDescription>?>()
    val videoResult: LiveData<ArrayList<VideoDescription>?>
        get() = _videoResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    init {
        fetchVideos()
    }
    private fun fetchVideos() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected())
                when (val result = videoRepository.getVideoList()) {
                    is AppResult.Success -> {
                        _videoResult.postValue(result.data)
                        _errorMessage.postValue("")
                    }
                    is AppResult.Failure -> {
                        _errorMessage.postValue("Something went wrong!!")
                    }
                }
        }
    }
}