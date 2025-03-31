package com.mvnh.feature_finances.domain.model

internal data class TransferTransaction(
    val fromGoalId: Int,
    val toGoalId: Int,
    val amount: Int,
    val comment: String
)
