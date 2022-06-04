package com.example.videoplayer.data.api

import com.example.videoplayer.data.model.VideosResponse
import com.example.videoplayer.domain.AppResult
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("1d201694-a43c-4f52-a1eb-8f394b72f6b7")
    fun getVideos(): Call<VideosResponse>

}

