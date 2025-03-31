package com.mvnh.feature_feed.domain.repository

import com.mvnh.feature_feed.domain.model.Post

internal interface PostsRepository {
    suspend fun createPost(post: Post)
    suspend fun getPosts(): List<Post>
    suspend fun deletePost(postId: Int)
}
