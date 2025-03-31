package com.mvnh.feature_home.data.repository

import com.mvnh.common.data.repository.BaseRepository
import com.mvnh.feature_home.data.local.dao.ArticlesDao
import com.mvnh.feature_home.data.mapper.toEntity
import com.mvnh.feature_home.data.mapper.toModel
import com.mvnh.feature_home.data.network.api.ArticlesApi
import com.mvnh.feature_home.domain.model.Article
import com.mvnh.feature_home.domain.repository.ArticlesRepository

internal class IArticlesRepository(
    private val api: ArticlesApi,
    private val dao: ArticlesDao
) : ArticlesRepository, BaseRepository() {

    override suspend fun getArticles(): List<Article> = safeApiCall(
        apiCall = { api.getArticles() },
        transform = { it.toModel() }
    )

    override suspend fun cacheArticles(articles: List<Article>) {
        dao.clearArticles()
        dao.insertArticles(articles.map { it.toEntity() })
    }

    override suspend fun getCachedArticles(): List<Article> {
        return dao.getArticles().map { it.toModel() }
    }
}
