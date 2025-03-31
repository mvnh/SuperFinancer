package com.mvnh.feature_finances.domain.state

import com.mvnh.feature_finances.domain.model.Transaction

internal sealed class TransactionsState {
    data object Idle : TransactionsState()
    data object Loading : TransactionsState()
    data object TransferSuccess : TransactionsState()
    data object DepositSuccess : TransactionsState()
    data object WithdrawSuccess : TransactionsState()
    data class GetTransactionsSuccess(val transactions: List<Transaction>) : TransactionsState()
    data class Error(val message: String) : TransactionsState()
}
