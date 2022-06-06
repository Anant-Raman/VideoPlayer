package com.example.videoplayer.common.di.module

import android.content.Context
import com.example.videoplayer.BuildConfig
import com.example.videoplayer.common.helper.NetworkHelper
import com.example.videoplayer.data.api.ApiHelper
import com.example.videoplayer.data.api.ApiHelperImpl
import com.example.videoplayer.data.api.ApiService
import com.example.videoplayer.data.repository.VideoHistoryRepository
import com.example.videoplayer.data.repository.VideoRepository
import com.example.videoplayer.data.room.VideoHistoryDB
import com.example.videoplayer.data.room.VideoHistoryDBImpl
import com.example.videoplayer.data.room.VideoHistoryDao
import com.example.videoplayer.data.room.VideoPlayerRoomDatabase
import com.example.videoplayer.presentation.ui.dashboard.HistoryViewModel
import com.example.videoplayer.presentation.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    viewModel {
        HomeViewModel(get(), get(), get())
    }
    viewModel {
        HistoryViewModel(get())
    }

    single { VideoRepository(get()) }
    single { VideoHistoryRepository(get()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }

    single<VideoHistoryDB> {
        return@single VideoHistoryDBImpl(get())
    }

    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }

    single { provideVideoHistoryDao(get()) }
    single { provideDatabase() }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

private fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

fun provideVideoHistoryDao(database: VideoPlayerRoomDatabase): VideoHistoryDao {
    return database.videoHistoryDao()
}

fun provideDatabase(): VideoPlayerRoomDatabase =
    VideoPlayerRoomDatabase.getDatabase()
