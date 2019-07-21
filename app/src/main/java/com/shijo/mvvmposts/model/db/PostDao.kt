package com.shijo.mvvmposts.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shijo.mvvmposts.model.Post

@Dao
interface PostDao {
    @get:Query("select * from post")
    val all: List<Post>

    @Insert
    fun insertAll(vararg post: Post)
}