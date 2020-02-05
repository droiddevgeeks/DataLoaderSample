package com.example.dataloadersample.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contentloader.ContentDownloader
import com.example.contentloader.callback.IDownloadStatus
import com.example.dataloadersample.api.PinBoard
import com.example.dataloadersample.base.core.BaseViewModel
import com.google.gson.Gson
import javax.inject.Inject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch


class MainViewModel @Inject constructor(
    private var contentDownloader: ContentDownloader
) :
    BaseViewModel() {


    private val _pinBoardLiveData by lazy { MutableLiveData<List<PinBoard>>() }
    val pinBoardLiveData: LiveData<List<PinBoard>> by lazy { _pinBoardLiveData }

    private val _loadingState by lazy { MutableLiveData<Boolean>() }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }


    /**
     * This method fetch data from server
     * @param url : api url to fetch data
     * this launch will work on IO thread as in BaseViewModel we have set to IO
     */
    fun fetchJsonData(url: String) {
        _loadingState.postValue(true)
        launch {
            contentDownloader
                .setUrl(url)
                .setCallBack(callback = object : IDownloadStatus<String> {
                    override fun onSuccess(content: String) {
                        _loadingState.postValue(false)
                        val listType = object : TypeToken<List<PinBoard>>() {}.type
                        val pinBoard: List<PinBoard> = Gson().fromJson(content, listType)
                        _pinBoardLiveData.postValue(pinBoard)
                    }

                    override fun onError(throwable: Throwable) {
                        _loadingState.postValue(false)
                        Log.v("Download Error", throwable.message ?: "")
                    }
                })
                .fetchContent()
        }
    }

    override fun onCleared() {
        super.onCleared()
        contentDownloader.cancel()
    }
}