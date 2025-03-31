package com.mvnh.feature_finances.data.mapper

import com.mvnh.common.util.formatDate
import com.mvnh.common.util.parseDateToMillis
import com.mvnh.feature_finances.data.local.entity.GoalEntity
import com.mvnh.feature_finances.domain.model.AddGoal
import com.mvnh.feature_finances.domain.model.Goal

internal fun AddGoal.toEntity() = GoalEntity(
    name = name,
    targetAmount = targetAmount,
    tillDate = tillDate
)

internal fun GoalEntity.toModel() = Goal(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    tillDate = formatDate(tillDate, withoutYear = false),
)

internal fun Goal.toEntity() = GoalEntity(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    tillDate = parseDateToMillis(tillDate)
)
