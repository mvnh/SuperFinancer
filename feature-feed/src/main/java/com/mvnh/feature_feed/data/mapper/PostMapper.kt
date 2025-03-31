package com.mvnh.feature_feed.data.mapper

import com.mvnh.feature_feed.data.local.entity.PostEntity
import com.mvnh.feature_feed.domain.model.Post

internal fun PostEntity.toModel() = Post(
    author = author,
    content = content,
    tags = tags.split(", ").toList(),
    linkedArticleTitle = linkedArticleTitle,
    linkedArticleUrl = linkedArticleUrl,
    isBookmarked = isBookmarked
)

internal fun Post.toEntity() = PostEntity(
    author = author,
    content = content,
    tags = tags.joinToString(", "),
    linkedArticleTitle = linkedArticleTitle,
    linkedArticleUrl = linkedArticleUrl,
    isBookmarked = isBookmarked
)
