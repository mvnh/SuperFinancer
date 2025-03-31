package com.mvnh.feature_finances.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mvnh.feature_finances.data.local.entity.GoalEntity

@Dao
internal interface GoalsDao {

    @Insert
    suspend fun insertGoal(goal: GoalEntity)

    @Query("SELECT * FROM goals ORDER BY timestamp DESC")
    suspend fun getGoals(): List<GoalEntity>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: Int): GoalEntity

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Delete
    suspend fun deleteGoal(goal: GoalEntity)
}
