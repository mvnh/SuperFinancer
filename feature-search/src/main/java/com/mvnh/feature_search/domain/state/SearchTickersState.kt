package com.mvnh.feature_search.domain.state

import com.mvnh.feature_search.domain.model.SearchTicker

internal sealed class SearchTickersState {
    data object Idle : SearchTickersState()
    data class Loading(val query: String) : SearchTickersState()
    data class SearchTickersSuccess(val tickers: List<SearchTicker>) : SearchTickersState()
    data class SearchTickersError(val message: String) : SearchTickersState()
}
