package com.example.contentloader.di.module

import com.example.contentloader.network.HttpOperation
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideHttpOperation(): HttpOperation = HttpOperation()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
}