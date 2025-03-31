package com.mvnh.feature_feed.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mvnh.feature_feed.data.local.entity.PostEntity

@Dao
internal interface PostsDao {

    @Insert
    suspend fun insertPost(post: PostEntity)

    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity

    @Delete
    suspend fun deletePost(post: PostEntity)
}
