package com.mvnh.feature_feed.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
internal data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "tags") val tags: String,
    @ColumnInfo(name = "linked_article_title") val linkedArticleTitle: String? = null,
    @ColumnInfo(name = "linked_article_url") val linkedArticleUrl: String? = null,
    @ColumnInfo(name = "is_bookmarked") val isBookmarked: Boolean
)
