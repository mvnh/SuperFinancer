package com.mvnh.feature_feed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvnh.feature_feed.data.local.dao.PostsDao
import com.mvnh.feature_feed.data.local.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1)
internal abstract class FeedDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}
