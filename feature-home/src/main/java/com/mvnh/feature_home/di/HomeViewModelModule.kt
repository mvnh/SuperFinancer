package com.mvnh.feature_home.di

import com.mvnh.feature_home.ui.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}
