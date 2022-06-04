package com.example.videoplayer.common.di.module

import com.example.videoplayer.data.repository.VideoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { VideoRepository(get()) }
}
