package com.example.videoplayer.data.room

import com.example.videoplayer.domain.AppError
import com.example.videoplayer.domain.AppResult
import java.sql.Timestamp

interface VideoHistoryDB {
    suspend fun fetchVideoHistory(): AppResult<ArrayList<VideoHistory>>
    suspend fun saveVideoHistory(videoHistory: VideoHistory)
    suspend fun hasVideoInHistory(videoName: String): Boolean
    suspend fun updateViewsInVideoHistory(views: Int, videoName: String)
    suspend fun updateLastWatchInVideoHistory(time: Timestamp, videoName: String)
}

class VideoHistoryDBImpl(private val videoHistoryDao: VideoHistoryDao) : VideoHistoryDB {
    override suspend fun fetchVideoHistory(): AppResult<ArrayList<VideoHistory>> {
        val videoList = videoHistoryDao.fetchVideoHistory()
        return if (videoList != null) AppResult.Success(videoList as ArrayList<VideoHistory>)
        else AppResult.Failure(AppError("002", "No Result!!"))
    }

    override suspend fun saveVideoHistory(videoHistory: VideoHistory) {
        videoHistoryDao.saveVideoToHistory(videoHistory)
    }

    override suspend fun hasVideoInHistory(videoName: String): Boolean {
        return videoHistoryDao.isVideoInDB(videoName = videoName) != 0
    }

    override suspend fun updateViewsInVideoHistory(views: Int, videoName: String) {
        videoHistoryDao.updateViewsInHistory(views, videoName)
    }

    override suspend fun updateLastWatchInVideoHistory(time: Timestamp, videoName: String) {
        videoHistoryDao.updateLastWatchTime(time, videoName)
    }

}