package com.example.videoplayer.data.api

import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.domain.AppResult

interface ApiHelper {
    suspend fun getVideoList(): AppResult<ArrayList<VideoDescription>>
}