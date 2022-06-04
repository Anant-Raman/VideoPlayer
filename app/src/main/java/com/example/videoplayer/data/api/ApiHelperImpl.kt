package com.example.videoplayer.data.api

import com.example.videoplayer.data.model.VideoDescription
import com.example.videoplayer.data.model.VideosResponse
import com.example.videoplayer.domain.AppError
import com.example.videoplayer.domain.AppResult
import okhttp3.internal.concurrent.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override suspend fun getVideoList(): AppResult<ArrayList<VideoDescription>> {

        val result = apiService.getVideos().awaitResponse()
        return if (result.isSuccessful){
            val arr = arrayListOf<VideoDescription>()

            result.body()?.response?.videos?.values?.forEach {
                arr.add(it)
            }
            AppResult.Success(arr)
        }else{
            AppResult.Failure(AppError("001","Error found!!"))
        }


//        apiService.getVideos().enqueue(object : Callback<VideosResponse> {
//
//            override fun onResponse(
//                call: Call<VideosResponse>,
//                response: Response<VideosResponse>
//            ) {
//                val arr = arrayListOf<VideoDescription>()
//
//                response.body()?.response?.videos?.values?.forEach {
//                    arr.add(it)
//                }
//                result = arr
//            }
//
//            override fun onFailure(call: Call<VideosResponse>, t: Throwable) {
//
//            }
//        })
    }

}


//        var response: Call<VideosResponse>? = null
//        var exception: Exception? = null
//        try {
//            response = apiService.getVideos()
//
//            response.awaitResponse()
//        } catch (e: Exception) {
//            exception = e
//        }
//
//        return if(exception == null) AppResult.Success(response.)
