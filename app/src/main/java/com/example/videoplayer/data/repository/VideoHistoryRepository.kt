package com.example.videoplayer.data.repository

import com.example.videoplayer.data.room.VideoHistory
import com.example.videoplayer.data.room.VideoHistoryDB
import com.example.videoplayer.domain.AppResult
import java.sql.Timestamp

class VideoHistoryRepository(private val videoHistoryDB: VideoHistoryDB) {
    suspend fun getVideoListFromDb(): AppResult<ArrayList<VideoHistory>> =
        videoHistoryDB.fetchVideoHistory()

    suspend fun saveVideoToDb(videoHistory: VideoHistory) =
        videoHistoryDB.saveVideoHistory(videoHistory = videoHistory)

    suspend fun isVideoInHistory(videoName: String) =
        videoHistoryDB.hasVideoInHistory(videoName = videoName)

    suspend fun updateViewsInHistory(views: Int, videoName: String) {
        videoHistoryDB.updateViewsInVideoHistory(views, videoName)
    }

    suspend fun updateLastWatchTimeInHistory(lastViewed: Timestamp, videoName: String) {
        videoHistoryDB.updateLastWatchInVideoHistory(lastViewed, videoName)
    }
}