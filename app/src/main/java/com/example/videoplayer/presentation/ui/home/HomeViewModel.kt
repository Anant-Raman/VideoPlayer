package com.example.videoplayer.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.common.helper.NetworkHelper
import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.data.repository.VideoHistoryRepository
import com.example.videoplayer.data.repository.VideoRepository
import com.example.videoplayer.data.room.VideoHistory
import com.example.videoplayer.domain.AppResult
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.launch
import java.sql.Timestamp

class HomeViewModel(
    private val videoRepository: VideoRepository,
    private val networkHelper: NetworkHelper,
    private val videoHistoryRepository: VideoHistoryRepository
) : ViewModel() {

    var playWhenReady = true
    var currentItem = 0
    var playbackPosition = 0L
    var mediaIndex = 0

    var list: List<VideoDescription> = emptyList()
    var player: ExoPlayer? = null

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

    fun saveVideoToHistory(videoDescription: VideoDescription) {
        viewModelScope.launch {

            if (videoDescription.title?.let { videoHistoryRepository.isVideoInHistory(it) } == true) {

                val videoDataFromDb = videoHistoryRepository.getVideoListFromDb()
                if (videoDataFromDb is AppResult.Success) {
                    val views =
                        videoDataFromDb.data.filter { it.videoName == videoDescription.title }
                            .get(0).views
                    views?.plus(1)
                        ?.let {
                            videoHistoryRepository.updateViewsInHistory(
                                it,
                                videoDescription.title.toString()
                            )
                        }
                    videoHistoryRepository.updateLastWatchTimeInHistory(
                        Timestamp(System.currentTimeMillis()),
                        videoDescription.title.toString()
                    )
                }
            } else {
                val videoHistory =
                    videoDescription.title?.let {
                        VideoHistory(
                            it,
                            videoDescription,
                            1,
                            Timestamp(System.currentTimeMillis())
                        )
                    }
                videoHistory?.let { videoHistoryRepository.saveVideoToDb(it) }
            }

            val videoDataFromDb = videoHistoryRepository.getVideoListFromDb()
            Log.i("Anant", videoDataFromDb.toString())
        }
    }
}