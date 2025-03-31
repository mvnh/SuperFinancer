package com.mvnh.feature_search.di

import com.mvnh.feature_search.data.repository.IArticleSearchRepository
import com.mvnh.feature_search.data.repository.ITickerSearchRepository
import com.mvnh.feature_search.domain.repository.ArticleSearchRepository
import com.mvnh.feature_search.domain.repository.TickerSearchRepository
import org.koin.dsl.module

val searchRepositoryModule = module {
    factory<ArticleSearchRepository> { IArticleSearchRepository(get()) }
    factory<TickerSearchRepository> { ITickerSearchRepository(get(), get()) }
}
