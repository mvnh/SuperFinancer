package com.mvnh.feature_finances.di

import androidx.room.Room
import com.mvnh.feature_finances.data.local.FinancesDatabase
import org.koin.dsl.module

val financesDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            FinancesDatabase::class.java,
            "feature-finances.db"
        ).build()
    }

    single { get<FinancesDatabase>().goalsDao() }
    single { get<FinancesDatabase>().transactionsDao() }
}
