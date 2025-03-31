package com.mvnh.feature_home.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
internal data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "abstract") val abstract: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "published_date") val publishedDate: String,
    @ColumnInfo(name = "geo_facet") val geoFacet: String,
    @ColumnInfo(name = "multimedia") val multimedia: String,
)
