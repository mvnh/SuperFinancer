package com.mvnh.feature_finances.domain.model

internal data class Transaction(
    val type: TransactionType,
    val fromGoalName: String,
    val toGoalName: String,
    val amount: Int,
    val date: String,
    val comment: String,
    val timestamp: Long
)

internal enum class TransactionType(val id: Int) {
    TRANSFER(1),
    DEPOSIT(2),
    WITHDRAW(3)
}
