package com.mvnh.feature_feed.domain.state

import com.mvnh.feature_feed.domain.model.Post

internal sealed class FeedState {
    data object Idle : FeedState()
    data object Loading : FeedState()
    data object CreatePostSuccess : FeedState()
    data class GetFeedSuccess(val posts: List<Post>) : FeedState()
    data object DeletePostSuccess : FeedState()
    data class Error(val message: String) : FeedState()
}
