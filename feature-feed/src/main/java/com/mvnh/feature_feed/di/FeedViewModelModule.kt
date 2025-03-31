package com.mvnh.feature_feed.di

import com.mvnh.feature_feed.ui.viewmodel.FeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val feedViewModelModule = module {
    viewModel { FeedViewModel(get(), get(), get()) }
}
