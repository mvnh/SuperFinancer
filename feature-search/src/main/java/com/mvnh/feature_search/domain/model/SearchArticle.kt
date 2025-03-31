package com.mvnh.feature_search.domain.model

internal data class SearchArticle(
    val webUrl: String,
    val snippet: String,
    val leadParagraph: String,
    val source: String,
    val multimedia: String,
    val publishedDate: String
)
