package com.example.videoplayer.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videoplayer.data.model.VideoDescription
import java.sql.Timestamp

@Entity(tableName = "VideoHistoryTable")
data class VideoHistory(
    @PrimaryKey @ColumnInfo(name = "videoName") val videoName: String,
    @ColumnInfo(name = "videoDescription") val videoDescription: VideoDescription,
    @ColumnInfo(name = "views") val views: Int? = null,
    @ColumnInfo(name = "lastViewed") val lastViewed: Timestamp?
)