package com.mvnh.feature_finances.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvnh.feature_finances.data.local.dao.GoalsDao
import com.mvnh.feature_finances.data.local.dao.TransactionsDao
import com.mvnh.feature_finances.data.local.entity.GoalEntity
import com.mvnh.feature_finances.data.local.entity.TransactionEntity

@Database(
    entities = [GoalEntity::class, TransactionEntity::class],
    version = 1
)
internal abstract class FinancesDatabase : RoomDatabase() {
    abstract fun goalsDao(): GoalsDao
    abstract fun transactionsDao(): TransactionsDao
}
