package com.mvnh.feature_home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvnh.feature_home.data.local.dao.ArticlesDao
import com.mvnh.feature_home.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
internal abstract class HomeDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticlesDao
}
