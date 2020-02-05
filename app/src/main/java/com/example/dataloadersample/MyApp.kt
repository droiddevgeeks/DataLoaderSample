package com.example.dataloadersample

import com.example.dataloadersample.di.component.DaggerAppComponent
import dagger.android.DaggerApplication

class MyApp : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        .build()

}