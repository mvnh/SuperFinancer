package com.mvnh.feature_finances.domain.model

internal data class AddGoal(
    val name: String,
    val targetAmount: Int,
    val tillDate: Long
)
