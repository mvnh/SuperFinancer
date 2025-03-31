package com.mvnh.feature_home.domain.repository

import com.mvnh.feature_home.domain.model.Article

internal interface ArticlesRepository {
    suspend fun getArticles(): List<Article>
    suspend fun cacheArticles(articles: List<Article>)
    suspend fun getCachedArticles(): List<Article>
}
