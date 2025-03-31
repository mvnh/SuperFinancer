package com.mvnh.feature_finances.data.repository

import com.mvnh.feature_finances.data.local.dao.GoalsDao
import com.mvnh.feature_finances.data.local.dao.TransactionsDao
import com.mvnh.feature_finances.data.mapper.toEntity
import com.mvnh.feature_finances.data.mapper.toModel
import com.mvnh.feature_finances.domain.model.DepositTransaction
import com.mvnh.feature_finances.domain.model.Transaction
import com.mvnh.feature_finances.domain.model.TransferTransaction
import com.mvnh.feature_finances.domain.model.WithdrawTransaction
import com.mvnh.feature_finances.domain.repository.TransactionsRepository

internal class ITransactionsRepository(
    private val goalsDao: GoalsDao,
    private val transactionsDao: TransactionsDao
) : TransactionsRepository {

    override suspend fun transfer(transaction: TransferTransaction) {
        val fromGoal = goalsDao.getGoalById(transaction.fromGoalId)
        val toGoal = goalsDao.getGoalById(transaction.toGoalId)

        goalsDao.updateGoal(fromGoal.copy(currentAmount = fromGoal.currentAmount - transaction.amount))
        goalsDao.updateGoal(toGoal.copy(currentAmount = toGoal.currentAmount + transaction.amount))

        transactionsDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deposit(transaction: DepositTransaction) {
        val goal = goalsDao.getGoalById(transaction.goalId)
        goalsDao.updateGoal(goal.copy(currentAmount = goal.currentAmount + transaction.amount))

        transactionsDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun withdraw(transaction: WithdrawTransaction) {
        val goal = goalsDao.getGoalById(transaction.goalId)
        goalsDao.updateGoal(goal.copy(currentAmount = goal.currentAmount - transaction.amount))

        transactionsDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun getTransactions(): List<Transaction> {
        val goals = goalsDao.getGoals().map { it.toModel() }
        return transactionsDao.getTransactions().map { it.toModel(goals) }
    }
}
