package com.example.contentloader

import android.util.Log
import com.example.contentloader.callback.IDownloadStatus
import com.example.contentloader.network.Downloader
import java.nio.charset.Charset
import javax.inject.Inject


class ContentDownloader @Inject constructor() : Downloader() {

    private lateinit var callback: IDownloadStatus<String>
    private lateinit var url: String

    fun fetchContent() {
        Log.v("Task Content on", Thread.currentThread().name)
        require(url.isNotEmpty()) { "Url should not be empty." }
        startDownload(url)
    }

    fun setUrl(url: String): ContentDownloader {
        this.url = url
        return this
    }

    fun setCallBack(callback: IDownloadStatus<String>): ContentDownloader {
        this.callback = callback
        return this
    }

    fun cancel() {
        cancelDownload()
    }

    override fun networkResponse(result: ByteArray?, error: Throwable?) {
        result?.let { callback.onSuccess(String(it, Charset.defaultCharset())) }
        error?.let { callback.onError(it) }
    }
}