package com.mvnh.feature_finances.di

import com.mvnh.feature_finances.domain.usecase.AddGoalUseCase
import com.mvnh.feature_finances.domain.usecase.DeleteGoalUseCase
import com.mvnh.feature_finances.domain.usecase.DepositTransactionUseCase
import com.mvnh.feature_finances.domain.usecase.GetGoalsUseCase
import com.mvnh.feature_finances.domain.usecase.GetSavingsInfoUseCase
import com.mvnh.feature_finances.domain.usecase.GetTransactionsUseCase
import com.mvnh.feature_finances.domain.usecase.TransferTransactionUseCase
import com.mvnh.feature_finances.domain.usecase.WithdrawTransactionUseCase
import org.koin.dsl.module

val financesUseCaseModule = module {
    factory { AddGoalUseCase(get()) }
    factory { DeleteGoalUseCase(get()) }
    factory { GetGoalsUseCase(get()) }
    factory { GetTransactionsUseCase(get()) }
    factory { TransferTransactionUseCase(get()) }
    factory { DepositTransactionUseCase(get()) }
    factory { WithdrawTransactionUseCase(get()) }
    factory { GetSavingsInfoUseCase(get()) }
}
