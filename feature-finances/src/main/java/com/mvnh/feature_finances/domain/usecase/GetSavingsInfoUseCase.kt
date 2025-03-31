package com.mvnh.feature_finances.domain.usecase

import android.util.Log
import com.mvnh.feature_finances.domain.repository.SavingsRepository
import com.mvnh.feature_finances.domain.state.SavingsState

internal class GetSavingsInfoUseCase(private val repository: SavingsRepository) {

    suspend operator fun invoke(): Result<SavingsState> = try {
        val info = repository.getSavingsInfo()
        Log.d(TAG, "invoke: $info")
        Result.success(SavingsState.GetSavingsInfoSuccess(info))
    } catch (e: Exception) {
        Log.d(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetSavingsInfoUseCase"
    }
}
