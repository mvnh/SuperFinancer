package com.mvnh.feature_search.domain.usecase

import android.util.Log
import com.mvnh.feature_search.domain.repository.ArticleSearchRepository
import com.mvnh.feature_search.domain.state.SearchArticlesState

internal class SearchArticlesUseCase(private val repository: ArticleSearchRepository) {

    suspend operator fun invoke(query: String): Result<SearchArticlesState> = try {
        val result = repository.searchArticles(query)
        Log.d(TAG, "invoke: $result")
        Result.success(SearchArticlesState.SearchArticlesSuccess(result))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "SearchArticlesUseCase"
    }
}
