package com.mvnh.feature_feed.di

import androidx.room.Room
import com.mvnh.feature_feed.data.local.FeedDatabase
import org.koin.dsl.module

val feedDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            FeedDatabase::class.java,
            "feature-feed.db"
        ).build()
    }

    single { get<FeedDatabase>().postsDao() }
}
