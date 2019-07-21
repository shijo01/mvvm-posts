package com.shijo.mvvmposts.model.network

import com.shijo.mvvmposts.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {
    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

}