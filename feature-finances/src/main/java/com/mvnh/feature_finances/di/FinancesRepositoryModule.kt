package com.mvnh.feature_finances.di

import com.mvnh.feature_finances.data.repository.IGoalsRepository
import com.mvnh.feature_finances.data.repository.ISavingsRepository
import com.mvnh.feature_finances.data.repository.ITransactionsRepository
import com.mvnh.feature_finances.domain.repository.GoalsRepository
import com.mvnh.feature_finances.domain.repository.SavingsRepository
import com.mvnh.feature_finances.domain.repository.TransactionsRepository
import org.koin.dsl.module

val financesRepositoryModule = module {
    factory<GoalsRepository> { IGoalsRepository(get(), get()) }
    factory<TransactionsRepository> { ITransactionsRepository(get(), get()) }
    factory<SavingsRepository> { ISavingsRepository(get(), get()) }
}
