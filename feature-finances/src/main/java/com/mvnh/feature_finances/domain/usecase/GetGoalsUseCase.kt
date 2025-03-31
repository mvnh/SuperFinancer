package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.repository.GoalsRepository
import com.mvnh.feature_finances.domain.state.GoalsState

internal class GetGoalsUseCase(private val repository: GoalsRepository) {

    suspend operator fun invoke(): Result<GoalsState> = try {
        val goals = repository.getGoals()
        Log.d(TAG, "invoke: $goals")
        Result.success(GoalsState.GetGoalsSuccess(goals))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetGoalsUseCase"
    }
}
