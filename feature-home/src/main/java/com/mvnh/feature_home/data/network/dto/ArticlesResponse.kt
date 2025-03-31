package com.mvnh.feature_home.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ArticlesResponse(
    @SerialName("results") val articles: List<ArticleDto>
)

@Serializable
internal data class ArticleDto(
    val title: String,
    val abstract: String,
    val url: String,
    val source: String,
    @SerialName("published_date") val publishedDate: String,
    @SerialName("geo_facet") val geoFacet: List<String>,
    val multimedia: List<MultimediaDto>
)

@Serializable
data class MultimediaDto(
    val url: String,
)
