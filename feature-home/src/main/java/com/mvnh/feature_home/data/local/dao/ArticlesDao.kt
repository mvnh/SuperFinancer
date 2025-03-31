package com.mvnh.feature_home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvnh.feature_home.data.local.entity.ArticleEntity

@Dao
internal interface ArticlesDao {

    @Query("SELECT * FROM articles ORDER BY published_date DESC")
    suspend fun getArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearArticles()
}
