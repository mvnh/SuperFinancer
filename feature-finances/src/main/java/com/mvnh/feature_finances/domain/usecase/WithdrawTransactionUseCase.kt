package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.model.WithdrawTransaction
import com.mvnh.feature_finances.domain.repository.TransactionsRepository
import com.mvnh.feature_finances.domain.state.TransactionsState

internal class WithdrawTransactionUseCase(private val repository: TransactionsRepository) {

    suspend operator fun invoke(
        fromGoalId: Int,
        amount: Int,
        comment: String
    ): Result<TransactionsState> = try {
        val transactionModel = WithdrawTransaction(
            goalId = fromGoalId,
            amount = amount,
            comment = comment
        )
        repository.withdraw(transactionModel)
        Log.d(TAG, "invoke: Withdraw successful")
        Result.success(TransactionsState.WithdrawSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: Withdraw failed", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "TransferTransactionUseCase"
    }
}
