package com.mvnh.feature_home.data.repository

import android.content.Context
import com.mvnh.common.data.network.api.MarketApi
import com.mvnh.common.data.repository.BaseRepository
import com.mvnh.feature_home.R
import com.mvnh.feature_home.data.mapper.toModel
import com.mvnh.feature_home.domain.model.CompanyProfile
import com.mvnh.feature_home.domain.model.PriceChangeType
import com.mvnh.feature_home.domain.model.Quote
import com.mvnh.feature_home.domain.repository.MarketRepository
import kotlinx.serialization.json.Json

internal class IMarketRepository(
    private val api: MarketApi,
    private val context: Context
) : MarketRepository, BaseRepository() {

    override suspend fun getQuoteForStock(symbol: String): Quote = safeApiCall(
        apiCall = { api.getQuoteForStock(symbol) },
        transform = {
            val priceChangeType = it.change?.let { change ->
                when {
                    change > 0 -> PriceChangeType.POSITIVE
                    change < 0 -> PriceChangeType.NEGATIVE
                    else -> PriceChangeType.NEUTRAL
                }
            }

            it.toModel(priceChangeType)
        }
    )

    override suspend fun getCompanyProfile(symbol: String): CompanyProfile = safeApiCall(
        apiCall = { api.getCompanyProfile(symbol) },
        transform = { it.toModel() }
    )

    override suspend fun getHardcodedTickers(): List<String> {
        val jsonString = context.resources.openRawResource(R.raw.tickers)
            .bufferedReader()
            .use { it.readText() }

        return Json.decodeFromString(jsonString)
    }
}
