package com.mvnh.feature_finances.di

import com.mvnh.feature_finances.ui.viewmodel.FinancesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val financesViewModelModule = module {
    viewModel {
        FinancesViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
