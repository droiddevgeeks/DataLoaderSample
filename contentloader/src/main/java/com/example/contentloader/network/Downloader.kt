package com.example.contentloader.network

import com.example.contentloader.di.component.DaggerContentComponent
import okhttp3.Call
import okhttp3.Request

abstract class Downloader : HttpCallback {

    private val component = DaggerContentComponent.builder().build()

    private lateinit var call: Call
    private lateinit var url: String


    protected fun startDownload(url: String) {
        val request = Request.Builder().url(url).get().build()
        this.call = component.getOkHttpclient().newCall(request)
        this.url = url
        if (::call.isInitialized) {
            component.getHttpOperation().makeRequest(call, this@Downloader)
        }
    }

    protected fun cancelDownload() {
        if (::call.isInitialized && !call.isCanceled() && call.isExecuted()) {
            call.cancel()
        }
    }

    /**
     * This Response is on HTTP thread.
     * While using this data, we have to switch to main thread
     */
    override fun onResponse(result: ByteArray?, error: Throwable?) {
        networkResponse(result, error)
    }

    abstract fun networkResponse(result: ByteArray?, error: Throwable?)

}