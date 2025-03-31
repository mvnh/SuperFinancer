package com.mvnh.feature_finances.data.repository

import android.util.Log
import com.mvnh.feature_finances.data.local.dao.GoalsDao
import com.mvnh.feature_finances.data.local.dao.TransactionsDao
import com.mvnh.feature_finances.domain.model.SavingsInfo
import com.mvnh.feature_finances.domain.repository.SavingsRepository

internal class ISavingsRepository(
    private val goalsDao: GoalsDao,
    private val transactionsDao: TransactionsDao
) : SavingsRepository {

    override suspend fun getSavingsInfo(): SavingsInfo {
        val total = goalsDao.getGoals().sumOf { it.currentAmount }
        val totalTarget = goalsDao.getGoals().sumOf { it.targetAmount }
        val totalIncome = transactionsDao.getTransactions().sumOf { it.amount }
        val avgIncome = totalIncome / 12
        Log.d("sldkjf", "getSavingsInfo: total=$total, totalTarget=$totalTarget, avgIncome=$avgIncome")
        val progressPercentage = (total.toDouble() / totalTarget) * 100
        Log.d("sldkjf", "getSavingsInfo: progressPercentage=$progressPercentage")
        return SavingsInfo(total, progressPercentage.toInt(), avgIncome)
    }
}
