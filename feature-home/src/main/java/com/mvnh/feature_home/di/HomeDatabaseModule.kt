package com.mvnh.feature_home.di

import androidx.room.Room
import com.mvnh.feature_home.data.local.HomeDatabase
import org.koin.dsl.module

val homeDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            HomeDatabase::class.java,
            "feature-home.db"
        ).build()
    }

    single { get<HomeDatabase>().articleDao() }
}
