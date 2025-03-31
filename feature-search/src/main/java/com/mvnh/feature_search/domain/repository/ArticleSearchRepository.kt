package com.mvnh.feature_search.domain.repository

import com.mvnh.feature_search.domain.model.SearchArticle

internal fun interface ArticleSearchRepository {
    suspend fun searchArticles(query: String): List<SearchArticle>
}
