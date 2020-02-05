package com.example.contentloader.di.component

import com.example.contentloader.di.module.NetworkModule
import com.example.contentloader.network.HttpOperation
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ContentComponent {

    fun getHttpOperation(): HttpOperation
    fun getOkHttpclient(): OkHttpClient
}