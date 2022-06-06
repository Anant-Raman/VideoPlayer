package com.example.videoplayer.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.data.repository.VideoHistoryRepository
import com.example.videoplayer.data.room.VideoHistory
import com.example.videoplayer.domain.AppResult
import kotlinx.coroutines.launch

class HistoryViewModel(private val videoHistoryRepository: VideoHistoryRepository) : ViewModel() {

    var list: List<VideoHistory> = emptyList()
    var sort = Sort.Views

    private val _videoDbResult = MutableLiveData<ArrayList<VideoHistory>?>()
    val videoDbResult: LiveData<ArrayList<VideoHistory>?>
        get() = _videoDbResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    init {
        fetchVideosFromDB()
    }

    fun fetchVideosFromDB() {
        viewModelScope.launch {
            when (val result = videoHistoryRepository.getVideoListFromDb()) {
                is AppResult.Success -> {
                    _videoDbResult.postValue(result.data)
                    _errorMessage.postValue("")
                }
                is AppResult.Failure -> {
                    _errorMessage.postValue("Something went wrong!!")
                }
            }
        }
    }
}