package com.mvnh.feature_feed.domain.usecase

import android.util.Log
import com.mvnh.feature_feed.domain.repository.PostsRepository
import com.mvnh.feature_feed.domain.state.FeedState

internal class DeletePostUseCase(private val repository: PostsRepository) {

    suspend operator fun invoke(postId: Int): Result<FeedState> = try {
        repository.deletePost(postId)
        Log.d(TAG, "invoke: Post deleted successfully")
        Result.success(FeedState.DeletePostSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "DeletePostUseCase"
    }
}
