package com.example.videoplayer.data.model

data class VideosResponse(
    var code: Int?,
    var response: VidResponse?
)

data class VidResponse(
    var videos: HashMap<String, VideoDescription>?
)

data class VideoDescription(
    var description: String?,
    var thumbnailURL: String?,
    var title: String?,
    var videoURL: String?
)