package com.mvnh.feature_search.data.repository

import com.mvnh.common.data.network.api.MarketApi
import com.mvnh.common.data.repository.BaseRepository
import com.mvnh.feature_search.data.mapper.toSearchTicker
import com.mvnh.feature_search.data.network.api.SymbolLookupApi
import com.mvnh.feature_search.domain.model.SearchTicker
import com.mvnh.feature_search.domain.repository.TickerSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class ITickerSearchRepository(
    private val symbolLookupApi: SymbolLookupApi,
    private val marketApi: MarketApi
) : TickerSearchRepository, BaseRepository() {

    override suspend fun searchTickers(query: String): List<SearchTicker> {
        val response = safeApiCall(
            apiCall = { symbolLookupApi.searchTickers(query) },
            transform = { it.result }
        ).take(10)

        return coroutineScope {
            response.map { symbolLookup ->
                async(Dispatchers.IO) {
                    val quote = safeApiCall(
                        apiCall = { marketApi.getQuoteForStock(symbolLookup.symbol) },
                        transform = { it }
                    )

                    if (quote.currentPrice > 0.01) {
                        symbolLookup.toSearchTicker(quote.currentPrice)
                    } else {
                        null
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }
}
