package com.mvnh.feature_feed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvnh.feature_feed.domain.state.FeedState
import com.mvnh.feature_feed.domain.usecase.CreatePostUseCase
import com.mvnh.feature_feed.domain.usecase.DeletePostUseCase
import com.mvnh.feature_feed.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class FeedViewModel(
    private val createPostUseCase: CreatePostUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _feed = MutableStateFlow<FeedState>(FeedState.Idle)
    val feed: StateFlow<FeedState> get() = _feed

    init {
        loadFeed()
    }

    fun createPost(
        author: String,
        content: String,
        tags: List<String>,
        linkedArticleTitle: String?,
        linkedArticleUrl: String?
    ) {
        viewModelScope.launch {
            _feed.value = FeedState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    createPostUseCase(
                        author = author,
                        content = content,
                        tags = tags,
                        linkedArticleTitle = linkedArticleTitle,
                        linkedArticleUrl = linkedArticleUrl
                    )
                }
            }.fold(
                onSuccess = { feedState ->
                    _feed.value = feedState.getOrNull()
                        ?: FeedState.Error("Failed to create post")

                    loadFeed()
                },
                onFailure = { e ->
                    _feed.value =
                        FeedState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            _feed.value = FeedState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getPostsUseCase() }
            }.fold(
                onSuccess = { postsState ->
                    _feed.value = postsState.getOrNull()
                        ?: FeedState.Error("Failed to get posts")
                },
                onFailure = { e ->
                    _feed.value =
                        FeedState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}
