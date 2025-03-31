package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.model.AddGoal
import com.mvnh.feature_finances.domain.repository.GoalsRepository
import com.mvnh.feature_finances.domain.state.GoalsState

internal class AddGoalUseCase(private val repository: GoalsRepository) {

    suspend operator fun invoke(
        name: String,
        target: Int,
        tillDate: Long
    ): Result<GoalsState> = try {
        val goalModel = AddGoal(name.trim(), target, tillDate)
        val result = repository.createGoal(goalModel)
        Log.d(TAG, "invoke: $result")
        Result.success(GoalsState.AddGoalSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "AddGoalUseCase"
    }
}
