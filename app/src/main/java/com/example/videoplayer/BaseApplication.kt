package com.example.videoplayer

import android.app.Application
import com.example.videoplayer.common.di.module.appModule
import com.example.videoplayer.common.di.module.repositoryModule
import com.example.videoplayer.common.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@BaseApplication)
            modules(listOf(appModule, repositoryModule))
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, repositoryModule, viewModelModule))


        }
    }
}