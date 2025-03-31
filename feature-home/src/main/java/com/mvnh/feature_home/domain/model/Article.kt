package com.mvnh.feature_home.domain.model

internal data class Article(
    val title: String,
    val abstract: String,
    val url: String,
    val source: String,
    val publishedDate: String,
    val geoFacet: String,
    val multimedia: String
)
