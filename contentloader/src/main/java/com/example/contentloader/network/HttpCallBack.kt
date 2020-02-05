package com.example.contentloader.network


interface HttpCallback {
    fun onResponse(result: ByteArray? = null, error: Throwable? = null)
}