package com.mvnh.feature_search.data.mapper

import com.mvnh.common.util.formatDate
import com.mvnh.feature_search.data.network.dto.SearchArticleResponse
import com.mvnh.feature_search.domain.model.SearchArticle

internal fun SearchArticleResponse.toModel() = this.response.docs.map {
    SearchArticle(
        webUrl = it.webUrl,
        snippet = it.snippet,
        leadParagraph = it.leadParagraph,
        source = it.source ?: "",
        multimedia = it.multimedia
            .firstOrNull {
                multimedia -> multimedia.subtype == "xlarge"
            }?.url ?: "",
        publishedDate = formatDate(it.publishedDate)
    )
}
