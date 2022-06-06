package com.example.videoplayer.data.room

import androidx.room.TypeConverter
import com.example.videoplayer.common.utils.JsonUtils
import com.example.videoplayer.common.utils.JsonUtils.convertJsonStringToObject
import com.example.videoplayer.data.model.VideoDescription
import java.sql.Timestamp

object RoomConverters {

    @TypeConverter
    @JvmStatic
    fun fromVideoHistory(value: VideoHistory?): String? {
        return JsonUtils.convertToJsonString(value)
    }

    @TypeConverter
    @JvmStatic
    fun toVideoHistory(value: String?): VideoHistory? {
        return convertJsonStringToObject<VideoHistory>(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromVideoDescription(value: VideoDescription?): String? {
        return JsonUtils.convertToJsonString(value)
    }

    @TypeConverter
    @JvmStatic
    fun toVideoDescription(value: String?): VideoDescription? {
        return convertJsonStringToObject<VideoDescription>(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromTimeStamp(value: Timestamp?): String? {
        return JsonUtils.convertToJsonString(value)
    }

    @TypeConverter
    @JvmStatic
    fun toTimeStamp(value: String?): Timestamp {
        return convertJsonStringToObject<Timestamp>(value) ?: Timestamp(System.currentTimeMillis())
    }
}