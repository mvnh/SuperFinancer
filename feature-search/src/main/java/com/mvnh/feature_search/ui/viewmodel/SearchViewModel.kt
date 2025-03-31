package com.mvnh.feature_search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvnh.feature_search.domain.state.SearchArticlesState
import com.mvnh.feature_search.domain.state.SearchTickersState
import com.mvnh.feature_search.domain.usecase.SearchArticlesUseCase
import com.mvnh.feature_search.domain.usecase.SearchTickersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SearchViewModel(
    private val searchTickersUseCase: SearchTickersUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase
) : ViewModel() {

    private val _searchTickers = MutableStateFlow<SearchTickersState>(SearchTickersState.Idle)
    val searchTickers: StateFlow<SearchTickersState> get() = _searchTickers

    private val _searchArticles = MutableStateFlow<SearchArticlesState>(SearchArticlesState.Idle)
    val searchArticles: StateFlow<SearchArticlesState> get() = _searchArticles

    fun performSearch(query: String) {
        viewModelScope.launch {
            val tickersDeferred = async { searchTickers(query) }
            val articlesDeferred = async { searchArticles(query) }
            awaitAll(tickersDeferred, articlesDeferred)
        }
    }

    private fun searchTickers(query: String) {
        viewModelScope.launch {
            _searchTickers.value = SearchTickersState.Loading(query)

            runCatching {
                withContext(Dispatchers.IO) { searchTickersUseCase(query) }
            }.fold(
                onSuccess = { tickers ->
                    _searchTickers.value = tickers.getOrNull()
                        ?: SearchTickersState.SearchTickersError("Failed to search tickers")
                },
                onFailure = { e ->
                    _searchTickers.value =
                        SearchTickersState.SearchTickersError(e.localizedMessage ?: "Failed to search tickers")
                }
            )
        }
    }

    private fun searchArticles(query: String) {
        viewModelScope.launch {
            _searchArticles.value = SearchArticlesState.Loading(query)

            runCatching {
                withContext(Dispatchers.IO) { searchArticlesUseCase(query) }
            }.fold(
                onSuccess = { articles ->
                    _searchArticles.value = articles.getOrNull()
                        ?: SearchArticlesState.SearchArticlesError("Failed to search articles")
                },
                onFailure = { e ->
                    _searchArticles.value =
                        SearchArticlesState.SearchArticlesError(e.localizedMessage ?: "Failed to search articles")
                }
            )
        }
    }
}
