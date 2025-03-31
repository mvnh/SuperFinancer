package com.mvnh.feature_finances.domain.state

import com.mvnh.feature_finances.domain.model.SavingsInfo

internal sealed class SavingsState {
    data object Idle : SavingsState()
    data object Loading : SavingsState()
    data class GetSavingsInfoSuccess(val info: SavingsInfo) : SavingsState()
    data class Error(val message: String) : SavingsState()
}
