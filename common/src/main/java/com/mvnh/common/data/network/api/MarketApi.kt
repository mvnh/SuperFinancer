package com.mvnh.common.data.network.api

import com.mvnh.common.data.network.dto.CompanyProfileDto
import com.mvnh.common.data.network.dto.QuoteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {

    @GET("quote")
    suspend fun getQuoteForStock(@Query("symbol") symbol: String): Response<QuoteDto>

    @GET("stock/profile2")
    suspend fun getCompanyProfile(@Query("symbol") symbol: String): Response<CompanyProfileDto>
}
