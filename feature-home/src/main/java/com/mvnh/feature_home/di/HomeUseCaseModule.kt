package com.mvnh.feature_home.di

import com.mvnh.feature_home.domain.usecase.GetArticlesUseCase
import com.mvnh.feature_home.domain.usecase.GetHardcodedTickersUseCase
import com.mvnh.feature_home.domain.usecase.GetTickerInfoUseCase
import org.koin.dsl.module

val homeUseCaseModule = module {
    factory { GetArticlesUseCase(get()) }
    factory { GetHardcodedTickersUseCase(get()) }
    factory { GetTickerInfoUseCase(get()) }
}
