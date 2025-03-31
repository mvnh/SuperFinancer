package com.mvnh.feature_home.data.mapper

import com.mvnh.common.util.formatDate
import com.mvnh.feature_home.data.local.entity.ArticleEntity
import com.mvnh.feature_home.data.network.dto.ArticleDto
import com.mvnh.feature_home.data.network.dto.ArticlesResponse
import com.mvnh.feature_home.domain.model.Article

internal fun ArticlesResponse.toModel() = articles.map { it.toModel() }

internal fun ArticleDto.toModel() = Article(
    title = title,
    abstract = abstract,
    url = url,
    source = source,
    publishedDate = formatDate(publishedDate),
    geoFacet = if (geoFacet.isNotEmpty()) geoFacet.first() else "",
    multimedia = multimedia.getOrNull(2)?.url ?: ""
)

//fun ArticleDto.toEntity() = ArticleEntity(
//    title = title,
//    abstract = abstract,
//    url = url,
//    source = source,
//    publishedDate = formatDate(publishedDate),
//    geoFacet = if (geoFacet.isNotEmpty()) geoFacet.first() else "",
//    multimedia = multimedia.getOrNull(2)?.url ?: ""
//)

internal fun Article.toEntity() = ArticleEntity(
    title = title,
    abstract = abstract,
    url = url,
    source = source,
    publishedDate = publishedDate,
    geoFacet = geoFacet,
    multimedia = multimedia
)

internal fun ArticleEntity.toModel() = Article(
    title = title,
    abstract = abstract,
    url = url,
    source = source,
    publishedDate = publishedDate,
    geoFacet = geoFacet,
    multimedia = multimedia
)
