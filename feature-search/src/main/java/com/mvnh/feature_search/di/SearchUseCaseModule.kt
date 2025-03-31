package com.mvnh.feature_search.di

import com.mvnh.feature_search.domain.usecase.SearchArticlesUseCase
import com.mvnh.feature_search.domain.usecase.SearchTickersUseCase
import org.koin.dsl.module

val searchUseCaseModule = module {
    factory { SearchArticlesUseCase(get()) }
    factory { SearchTickersUseCase(get()) }
}
