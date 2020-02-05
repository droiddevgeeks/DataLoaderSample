package com.example.contentloader.cache

import androidx.collection.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object MemoryCache : Cache {

    private const val cacheSize: Int = 10 * 1024
    private val cache by lazy { LruCache<String, ByteArray>(cacheSize) }

    override fun getData(key: String): ByteArray? {
        val value = cache[key]
        value?.let {
            return it
        }
        return null
    }

    override fun putData(key: String, value: ByteArray) {
        GlobalScope.launch(Dispatchers.IO) {
            if (cache[key] == null) {
                cache.put(key, value)
            }
        }
    }

    fun resizeCache(size: Int) {
        if (cacheSize < size) cache.resize(size)
    }

    override fun clearData() {
        cache.evictAll()
    }
}