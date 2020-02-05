package com.example.contentloader.cache

interface Cache {
    fun getData(key : String): ByteArray?
    fun putData(key:String,value: ByteArray)
    fun clearData()
}