package com.mvnh.feature_finances.data.repository

import com.mvnh.feature_finances.data.local.dao.GoalsDao
import com.mvnh.feature_finances.data.local.dao.TransactionsDao
import com.mvnh.feature_finances.data.mapper.toEntity
import com.mvnh.feature_finances.data.mapper.toModel
import com.mvnh.feature_finances.domain.model.AddGoal
import com.mvnh.feature_finances.domain.model.Goal
import com.mvnh.feature_finances.domain.repository.GoalsRepository

internal class IGoalsRepository(
    private val goalsDao: GoalsDao,
    private val transactionsDao: TransactionsDao
) : GoalsRepository {

    override suspend fun createGoal(goal: AddGoal) {
        goalsDao.insertGoal(goal.toEntity())
    }

    override suspend fun getGoals(): List<Goal> {
        return goalsDao.getGoals().map { it.toModel() }
    }

    override suspend fun updateGoal(goal: AddGoal) {
        goalsDao.updateGoal(goal.toEntity())
    }

    override suspend fun deleteGoal(goalId: Int) {
        goalsDao.deleteGoal(goalsDao.getGoalById(goalId))
        transactionsDao.deleteTransactionsByGoalId(goalId)
    }
}
