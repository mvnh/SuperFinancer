package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.model.DepositTransaction
import com.mvnh.feature_finances.domain.repository.TransactionsRepository
import com.mvnh.feature_finances.domain.state.TransactionsState

internal class DepositTransactionUseCase(private val repository: TransactionsRepository) {

    suspend operator fun invoke(
        toGoalId: Int,
        amount: Int,
        comment: String
    ): Result<TransactionsState> = try {
        val transactionModel = DepositTransaction(
            goalId = toGoalId,
            amount = amount,
            comment = comment
        )
        repository.deposit(transactionModel)
        Log.d(TAG, "invoke: Deposit successful")
        Result.success(TransactionsState.DepositSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: Deposit failed", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "TransferTransactionUseCase"
    }
}
