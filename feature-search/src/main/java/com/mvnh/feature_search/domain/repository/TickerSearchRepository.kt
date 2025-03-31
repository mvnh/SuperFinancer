package com.mvnh.feature_search.domain.repository

import com.mvnh.feature_search.domain.model.SearchTicker

internal fun interface TickerSearchRepository {
    suspend fun searchTickers(query: String): List<SearchTicker>
}
