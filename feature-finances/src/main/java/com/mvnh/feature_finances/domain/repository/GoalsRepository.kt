package com.mvnh.feature_finances.domain.repository

import com.mvnh.feature_finances.domain.model.AddGoal
import com.mvnh.feature_finances.domain.model.Goal

internal interface GoalsRepository {
    suspend fun createGoal(goal: AddGoal)
    suspend fun getGoals(): List<Goal>
    suspend fun updateGoal(goal: AddGoal)
    suspend fun deleteGoal(goalId: Int)
}
