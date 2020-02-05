package com.example.dataloadersample.di.component

import com.example.dataloadersample.MyApp
import com.example.dataloadersample.di.module.UiModule
import com.example.dataloadersample.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        UiModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApp): Builder

        fun build(): AppComponent
    }

    override fun inject(app: MyApp)
}