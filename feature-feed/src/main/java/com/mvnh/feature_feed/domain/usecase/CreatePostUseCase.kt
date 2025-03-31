package com.mvnh.feature_feed.domain.usecase

import android.util.Log
import com.mvnh.feature_feed.domain.model.Post
import com.mvnh.feature_feed.domain.repository.PostsRepository
import com.mvnh.feature_feed.domain.state.FeedState

internal class CreatePostUseCase(private val repository: PostsRepository) {

    suspend operator fun invoke(
        author: String,
        content: String,
        tags: List<String>,
        linkedArticleTitle: String?,
        linkedArticleUrl: String?
    ): Result<FeedState> = try {
        val postModel = Post(
            author = author,
            content = content,
            tags = tags,
            linkedArticleTitle = linkedArticleTitle,
            linkedArticleUrl = linkedArticleUrl
        )
        repository.createPost(postModel)
        Log.d(TAG, "invoke: Post created successfully")
        Result.success(FeedState.CreatePostSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "CreatePostUseCase"
    }
}
