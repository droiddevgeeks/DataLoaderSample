package com.example.dataloadersample.di.module

import com.example.dataloadersample.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class UiModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

}