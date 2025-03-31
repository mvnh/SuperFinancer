package com.mvnh.feature_feed.domain.model

internal data class Post(
    val author: String,
    val content: String,
    val tags: List<String>,
    val linkedArticleTitle: String?,
    val linkedArticleUrl: String?,
    val isBookmarked: Boolean = false
)
