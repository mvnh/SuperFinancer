package com.mvnh.feature_finances.domain.repository

import com.mvnh.feature_finances.domain.model.SavingsInfo

internal fun interface SavingsRepository {
    suspend fun getSavingsInfo(): SavingsInfo
}
