package com.mvnh.feature_home.domain.state

import com.mvnh.feature_home.domain.model.Ticker

internal sealed class TickersState {
    data object Idle : TickersState()
    data object Loading : TickersState()
    data class GetTickerInfoSuccess(val ticker: Ticker) : TickersState()
    data class GetTickersSuccess(val tickers: List<Ticker>) : TickersState()
    data class Error(val message: String) : TickersState()
}
