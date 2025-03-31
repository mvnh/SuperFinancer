package com.mvnh.feature_search.data.repository

import com.mvnh.common.data.repository.BaseRepository
import com.mvnh.feature_search.data.mapper.toModel
import com.mvnh.feature_search.data.network.api.ArticleSearchApi
import com.mvnh.feature_search.domain.model.SearchArticle
import com.mvnh.feature_search.domain.repository.ArticleSearchRepository

internal class IArticleSearchRepository(
    private val api: ArticleSearchApi
) : ArticleSearchRepository, BaseRepository() {

    override suspend fun searchArticles(query: String): List<SearchArticle> = safeApiCall(
        apiCall = { api.searchArticles(query) },
        transform = { it.toModel() }
    )
}
