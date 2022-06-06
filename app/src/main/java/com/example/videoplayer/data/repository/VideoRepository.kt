package com.example.videoplayer.data.repository

import com.example.videoplayer.data.api.ApiHelper
import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.domain.AppResult

class VideoRepository(private val apiHelper: ApiHelper) {
    suspend fun getVideoList(): AppResult<ArrayList<VideoDescription>> = apiHelper.getVideoList()
}