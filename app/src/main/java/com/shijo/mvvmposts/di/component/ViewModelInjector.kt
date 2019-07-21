package com.shijo.mvvmposts.di.component

import com.shijo.mvvmposts.di.NetworkModule
import com.shijo.mvvmposts.ui.post.PostListViewModel
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjector {
    fun inject(postListViewModel: PostListViewModel)


    @Component.Builder
    interface Builder {
        fun build() : ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }

}