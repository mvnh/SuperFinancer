package com.mvnh.feature_search.di

import com.mvnh.feature_search.ui.viewmodel.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }
}
