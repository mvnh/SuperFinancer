package com.mvnh.feature_home.domain.repository

import com.mvnh.feature_home.domain.model.CompanyProfile
import com.mvnh.feature_home.domain.model.Quote

internal interface MarketRepository {
    suspend fun getQuoteForStock(symbol: String): Quote
    suspend fun getCompanyProfile(symbol: String): CompanyProfile
    suspend fun getHardcodedTickers(): List<String>
}