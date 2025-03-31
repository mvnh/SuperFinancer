package com.mvnh.feature_search.domain.usecase

import android.util.Log
import com.mvnh.feature_search.domain.repository.TickerSearchRepository
import com.mvnh.feature_search.domain.state.SearchTickersState

internal class SearchTickersUseCase(private val repository: TickerSearchRepository) {

    suspend operator fun invoke(query: String): Result<SearchTickersState> = try {
        val result = repository.searchTickers(query)
        Log.d(TAG, "invoke: $result")
        Result.success(SearchTickersState.SearchTickersSuccess(result))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "SearchTickersUseCase"
    }
}
