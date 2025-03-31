package com.mvnh.feature_finances.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mvnh.feature_finances.data.local.entity.TransactionEntity

@Dao
internal interface TransactionsDao {

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    suspend fun getTransactions(): List<TransactionEntity>

    @Query("DELETE FROM transactions WHERE to_goal_id = :goalId OR from_goal_id = :goalId")
    suspend fun deleteTransactionsByGoalId(goalId: Int)
}
