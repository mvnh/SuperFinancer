package com.mvnh.feature_home.domain.usecase

import android.util.Log
import com.mvnh.feature_home.domain.repository.ArticlesRepository
import com.mvnh.feature_home.domain.state.ArticlesState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class GetArticlesUseCase(private val repository: ArticlesRepository) {

    suspend operator fun invoke(): Result<ArticlesState> = coroutineScope {
        try {
            val freshArticlesDeferred = async { repository.getArticles() }
            val cachedArticlesDeferred = async { repository.getCachedArticles() }

            val freshArticles = freshArticlesDeferred.await()
            val cachedArticles = cachedArticlesDeferred.await()

            val articles = if (cachedArticles.isEmpty()) {
                repository.cacheArticles(freshArticles)
                freshArticles
            } else {
                val isNewArticles = freshArticles.any { freshArticle ->
                    cachedArticles.none { it.title == freshArticle.title }
                }

                if (isNewArticles) {
                    repository.cacheArticles(freshArticles)
                    freshArticles
                } else {
                    cachedArticles
                }
            }

            Log.d(TAG, "invoke: $articles")
            Result.success(ArticlesState.GetArticlesSuccess(articles))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ", e)

            val cachedArticles = repository.getCachedArticles()
            if (cachedArticles.isNotEmpty()) {
                Log.d(TAG, "Returning cached articles due to error")
                Result.success(ArticlesState.GetArticlesSuccess(cachedArticles))
            } else {
                Result.failure(e)
            }
        }
    }

    companion object {
        private const val TAG = "GetArticlesUseCase"
    }
}
