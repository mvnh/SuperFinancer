package com.mvnh.feature_finances.domain.repository

import com.mvnh.feature_finances.domain.model.DepositTransaction
import com.mvnh.feature_finances.domain.model.Transaction
import com.mvnh.feature_finances.domain.model.TransferTransaction
import com.mvnh.feature_finances.domain.model.WithdrawTransaction

internal interface TransactionsRepository {
    suspend fun transfer(transaction: TransferTransaction)
    suspend fun deposit(transaction: DepositTransaction)
    suspend fun withdraw(transaction: WithdrawTransaction)
    suspend fun getTransactions(): List<Transaction>
}
