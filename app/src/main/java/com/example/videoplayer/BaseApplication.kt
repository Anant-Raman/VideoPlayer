package com.example.videoplayer

import android.app.Application
import android.content.Context
import com.example.videoplayer.common.di.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this
        startKoin {

            androidContext(this@BaseApplication)
            modules(listOf(appModule))
        }
    }

    companion object {
        lateinit var context: Context
        fun getApplicationContext() = context
    }
}