package com.example.contentloader.network

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HttpOperation {

    private lateinit var call: Call
    private lateinit var httpCallbacks: HttpCallback

    fun makeRequest(
        call: Call,
        httpCallbacks: HttpCallback
    ){

        this.call = call
        this.httpCallbacks = httpCallbacks
        makeRequest()
    }

    private fun makeRequest(){
        call.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                httpCallbacks.onResponse(error = e)
            }
            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful && response.body!=null){
                    httpCallbacks.onResponse(result = response.body?.bytes())
                }
            }
        })
    }

}