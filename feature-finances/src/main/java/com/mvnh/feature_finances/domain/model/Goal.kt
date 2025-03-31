package com.mvnh.feature_finances.domain.model

internal data class Goal(
    val id: Int,
    val name: String,
    val targetAmount: Int,
    val currentAmount: Int,
    val tillDate: String,
)
