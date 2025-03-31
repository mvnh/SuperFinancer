package com.mvnh.feature_finances.domain.model

internal data class DepositTransaction(
    val goalId: Int,
    val amount: Int,
    val comment: String
)
