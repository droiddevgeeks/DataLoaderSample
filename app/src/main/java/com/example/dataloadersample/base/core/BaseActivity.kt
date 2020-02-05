package com.example.dataloadersample.base.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    abstract fun getLayoutRes(): Int
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }
}