package com.example.videoplayer.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.videoplayer.BaseApplication

@Database(
    entities = [VideoHistory::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class VideoPlayerRoomDatabase : RoomDatabase() {
    abstract fun videoHistoryDao(): VideoHistoryDao

    companion object {
        fun getDatabase(): VideoPlayerRoomDatabase {

            return Room.databaseBuilder(
                BaseApplication.getApplicationContext(),
                VideoPlayerRoomDatabase::class.java,
                "videoPlayerRoomDatabase"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}