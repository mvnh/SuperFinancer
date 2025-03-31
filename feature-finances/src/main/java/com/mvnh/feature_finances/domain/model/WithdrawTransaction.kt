package com.mvnh.feature_finances.domain.model

internal data class WithdrawTransaction(
    val goalId: Int,
    val amount: Int,
    val comment: String
)
