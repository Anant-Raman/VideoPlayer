package com.example.videoplayer.common.di.module
//
import com.example.videoplayer.presentation.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
}