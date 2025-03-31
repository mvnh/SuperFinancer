package com.mvnh.feature_search.data.network.api

import com.mvnh.feature_search.data.network.dto.SearchArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ArticleSearchApi {

    @GET("articlesearch.json")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("fq") filterQuery: String = "news_desk:(\"Business Day\", \"Business\", \"Financial\", \"Personal Investing\", \"Politics\", \"Technology\", \"Your Money\")",
    ): Response<SearchArticleResponse>
}
