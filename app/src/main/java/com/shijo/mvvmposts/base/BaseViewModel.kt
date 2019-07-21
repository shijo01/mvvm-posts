package com.shijo.mvvmposts.base

import androidx.lifecycle.ViewModel
import com.shijo.mvvmposts.di.NetworkModule
import com.shijo.mvvmposts.di.component.DaggerViewModelInjector
import com.shijo.mvvmposts.ui.post.PostListViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
        }
    }

}