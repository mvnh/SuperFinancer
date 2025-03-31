package com.mvnh.feature_finances.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
internal data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "from_goal_id") val fromGoalId: Int = -1,
    @ColumnInfo(name = "to_goal_id") val toGoalId: Int = -1,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "comment") val comment: String = "",
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)
