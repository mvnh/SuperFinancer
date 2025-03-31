package com.mvnh.feature_feed.domain.usecase

import android.util.Log
import com.mvnh.feature_feed.domain.repository.PostsRepository
import com.mvnh.feature_feed.domain.state.FeedState

internal class GetPostsUseCase(private val repository: PostsRepository) {

    suspend operator fun invoke(): Result<FeedState> = try {
        val posts = repository.getPosts()
        Log.d(TAG, "invoke: $posts")
        Result.success(FeedState.GetFeedSuccess(posts))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetPostsUseCase"
    }
}
