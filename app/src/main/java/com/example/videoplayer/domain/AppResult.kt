package com.example.videoplayer.domain

sealed class AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Failure(val error: AppError) : AppResult<Nothing>()
}

data class AppError(val code: String, var message: String? = null)
