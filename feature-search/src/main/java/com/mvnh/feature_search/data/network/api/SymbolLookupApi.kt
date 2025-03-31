package com.mvnh.feature_search.data.network.api

import com.mvnh.feature_search.data.network.dto.SymbolLookupResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SymbolLookupApi {

    @GET("search")
    suspend fun searchTickers(
        @Query("q") query: String,
        @Query("exchange") exchange: String = "US"
    ): Response<SymbolLookupResponse>
}
