package com.mvnh.feature_home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvnh.feature_home.domain.state.ArticlesState
import com.mvnh.feature_home.domain.state.TickersState
import com.mvnh.feature_home.domain.usecase.GetArticlesUseCase
import com.mvnh.feature_home.domain.usecase.GetHardcodedTickersUseCase
import com.mvnh.feature_home.domain.usecase.GetTickerInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class HomeViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getHardcodedTickersUseCase: GetHardcodedTickersUseCase,
    private val getTickerInfoUseCase: GetTickerInfoUseCase
) : ViewModel() {

    private val _tickers = MutableStateFlow<TickersState>(TickersState.Idle)
    val tickers: StateFlow<TickersState> get() = _tickers

    private val _articles = MutableStateFlow<ArticlesState>(ArticlesState.Idle)
    val articles: StateFlow<ArticlesState> get() = _articles

    init {
        viewModelScope.launch {
            val tickersDeferred = async { loadTickers() }
            val articlesDeferred = async { loadArticles() }
            awaitAll(tickersDeferred, articlesDeferred)
        }
    }

    fun loadTickers() {
        viewModelScope.launch {
            _tickers.value = TickersState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getHardcodedTickersUseCase() }
            }.fold(
                onSuccess = { tickers ->
                    val tickersInfo = coroutineScope {
                        tickers.map { ticker ->
                            async(Dispatchers.IO) {
                                getTickerInfoUseCase(ticker)
                                    .getOrNull()
                                    ?.let { it as TickersState.GetTickerInfoSuccess }
                                    ?.ticker
                            }
                        }.awaitAll().filterNotNull()
                    }
                    _tickers.value = TickersState.GetTickersSuccess(tickersInfo)
                },
                onFailure = { e ->
                    _tickers.value =
                        TickersState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun loadArticles() {
        viewModelScope.launch {
            _articles.value = ArticlesState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getArticlesUseCase() }
            }.fold(
                onSuccess = { articlesState ->
                    _articles.value = articlesState.getOrNull()
                        ?: ArticlesState.Error("Unknown error")
                },
                onFailure = { e ->
                    _articles.value =
                        ArticlesState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}
