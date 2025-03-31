package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.repository.GoalsRepository
import com.mvnh.feature_finances.domain.state.GoalsState

internal class DeleteGoalUseCase(private val repository: GoalsRepository) {

    suspend operator fun invoke(goalId: Int): Result<GoalsState> = try {
        repository.deleteGoal(goalId)
        Log.d(TAG, "invoke: Goal deleted")
        Result.success(GoalsState.DeleteGoalSuccess)
    } catch (e: Exception) {
        Log.e(TAG, "invoke: Error deleting goal", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "DeleteGoalUseCase"
    }
}
