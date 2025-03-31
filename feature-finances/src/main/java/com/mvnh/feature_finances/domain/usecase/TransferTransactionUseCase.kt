package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.model.TransferTransaction
import com.mvnh.feature_finances.domain.repository.TransactionsRepository
import com.mvnh.feature_finances.domain.state.TransactionsState

internal class TransferTransactionUseCase(private val repository: TransactionsRepository) {

    suspend operator fun invoke(
        fromGoalId: Int,
        toGoalId: Int,
        amount: Int,
        comment: String
    ): Result<TransactionsState> = try {
        val transactionModel = TransferTransaction(
            fromGoalId = fromGoalId,
            toGoalId = toGoalId,
            amount = amount,
            comment = comment
        )
        repository.transfer(transactionModel)
        Log.d(TAG, "invoke: Transfer successful")
        Result.success(TransactionsState.TransferSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: Transfer failed", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "TransferTransactionUseCase"
    }
}
