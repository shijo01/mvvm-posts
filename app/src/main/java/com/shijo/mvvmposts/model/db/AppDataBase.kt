package com.shijo.mvvmposts.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shijo.mvvmposts.model.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun postDao(): PostDao

}