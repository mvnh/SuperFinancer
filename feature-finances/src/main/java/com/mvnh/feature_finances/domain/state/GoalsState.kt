package com.mvnh.feature_finances.domain.state

import com.mvnh.feature_finances.domain.model.Goal

internal sealed class GoalsState {
    data object Idle : GoalsState()
    data object Loading : GoalsState()
    data object AddGoalSuccess : GoalsState()
    data object DeleteGoalSuccess : GoalsState()
    data class GetGoalsSuccess(val goals: List<Goal>) : GoalsState()
    data class Error(val message: String) : GoalsState()
}
