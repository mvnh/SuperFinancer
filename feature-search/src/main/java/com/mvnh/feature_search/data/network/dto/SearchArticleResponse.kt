package com.mvnh.feature_search.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SearchArticleResponse(
    val response: SearchArticleResponseData
)

@Serializable
internal data class SearchArticleResponseData(
    val docs: List<SearchArticleDto>
)

@Serializable
internal data class SearchArticleDto(
    @SerialName("web_url") val webUrl: String,
    val snippet: String,
    @SerialName("lead_paragraph") val leadParagraph: String,
    val source: String? = null,
    val multimedia: List<SearchMultimediaDto>,
    @SerialName("pub_date") val publishedDate: String
)

@Serializable
internal data class SearchMultimediaDto(
    val subtype: String,
    val url: String
)
