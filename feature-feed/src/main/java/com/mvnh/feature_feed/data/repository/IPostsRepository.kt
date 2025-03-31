package com.mvnh.feature_feed.data.repository

import com.mvnh.feature_feed.data.local.dao.PostsDao
import com.mvnh.feature_feed.data.mapper.toEntity
import com.mvnh.feature_feed.data.mapper.toModel
import com.mvnh.feature_feed.domain.model.Post
import com.mvnh.feature_feed.domain.repository.PostsRepository

internal class IPostsRepository(private val dao: PostsDao) : PostsRepository {

    override suspend fun createPost(post: Post) {
        dao.insertPost(post.toEntity())
    }

    override suspend fun getPosts(): List<Post> {
        return dao.getPosts().map { it.toModel() }
    }

    override suspend fun deletePost(postId: Int) {
        dao.deletePost(dao.getPostById(postId))
    }
}
