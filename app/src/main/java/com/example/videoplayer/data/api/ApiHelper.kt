package com.example.videoplayer.data.api

import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.data.model.VideosResponse
import com.example.videoplayer.domain.AppResult
import retrofit2.Call

interface ApiHelper {
    suspend fun getVideoList():  AppResult<ArrayList<VideoDescription>>
}