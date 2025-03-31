package com.mvnh.feature_home.data.network.api

import com.mvnh.feature_home.data.network.dto.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface ArticlesApi {

    @GET("news/v3/content/all/all.json")
    suspend fun getArticles(): Response<ArticlesResponse>
}
