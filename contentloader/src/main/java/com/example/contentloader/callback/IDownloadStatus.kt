package com.example.contentloader.callback

interface IDownloadStatus<T> {
    fun onSuccess(content: T)
    fun onError(throwable: Throwable)
}