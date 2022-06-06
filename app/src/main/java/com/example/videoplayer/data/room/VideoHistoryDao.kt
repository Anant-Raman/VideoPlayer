package com.example.videoplayer.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.sql.Timestamp

@Dao
interface VideoHistoryDao {

    @Query("SELECT count(*) FROM VideoHistoryTable")
    suspend fun getVideoCount(): Int?

    @Query("SELECT * FROM VideoHistoryTable")
    suspend fun fetchVideoHistory(): List<VideoHistory>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVideoToHistory(videoHistory: VideoHistory)

    @Query("SELECT count(*) FROM VideoHistoryTable WHERE :videoName = videoName")
    suspend fun isVideoInDB(videoName: String): Int?

    @Query("UPDATE VideoHistoryTable SET views=:views WHERE videoName=:videoName")
    suspend fun updateViewsInHistory(views: Int, videoName: String)

    @Query("UPDATE VideoHistoryTable SET lastViewed=:lastViewed WHERE videoName=:videoName")
    suspend fun updateLastWatchTime(lastViewed: Timestamp, videoName: String)
}