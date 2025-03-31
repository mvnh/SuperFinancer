package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.repository.TransactionsRepository
import com.mvnh.feature_finances.domain.state.TransactionsState

internal class GetTransactionsUseCase(private val repository: TransactionsRepository) {

    suspend operator fun invoke(): Result<TransactionsState> = try {
        val transactions = repository.getTransactions()
        Log.d(TAG, "invoke: $transactions")
        Result.success(TransactionsState.GetTransactionsSuccess(transactions))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetTransactionsUseCase"
    }
}
