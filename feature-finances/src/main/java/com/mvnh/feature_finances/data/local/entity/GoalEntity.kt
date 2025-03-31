package com.mvnh.feature_finances.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
internal data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "target") val targetAmount: Int,
    @ColumnInfo(name = "current") val currentAmount: Int = 0,
    @ColumnInfo(name = "till_date") val tillDate: Long,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)
