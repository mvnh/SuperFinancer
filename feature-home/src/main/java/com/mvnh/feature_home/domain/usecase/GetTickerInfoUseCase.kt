package com.mvnh.feature_home.domain.usecase

import android.util.Log
import com.mvnh.feature_home.domain.model.Ticker
import com.mvnh.feature_home.domain.repository.MarketRepository
import com.mvnh.feature_home.domain.state.TickersState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class GetTickerInfoUseCase(private val repository: MarketRepository) {

    suspend operator fun invoke(symbol: String): Result<TickersState> = coroutineScope {
        try {
            val quoteDeferred = async { repository.getQuoteForStock(symbol) }
            val profileDeferred = async { repository.getCompanyProfile(symbol) }

            val quote = quoteDeferred.await()
            val companyProfile = profileDeferred.await()

            val result = Ticker(
                ticker = symbol,
                logoUrl = companyProfile.logoUrl,
                currentPrice = quote.currentPrice,
                changePercent = quote.changePercent,
                priceChangeType = quote.priceChangeType
            )
            Log.d(TAG, "invoke: $result")
            Result.success(TickersState.GetTickerInfoSuccess(result))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ", e)
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "GetTickerInfoUseCase"
    }
}
