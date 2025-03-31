package com.mvnh.feature_finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvnh.feature_finances.domain.state.GoalsState
import com.mvnh.feature_finances.domain.state.SavingsState
import com.mvnh.feature_finances.domain.state.TransactionsState
import com.mvnh.feature_finances.domain.usecase.AddGoalUseCase
import com.mvnh.feature_finances.domain.usecase.DeleteGoalUseCase
import com.mvnh.feature_finances.domain.usecase.DepositTransactionUseCase
import com.mvnh.feature_finances.domain.usecase.GetGoalsUseCase
import com.mvnh.feature_finances.domain.usecase.GetSavingsInfoUseCase
import com.mvnh.feature_finances.domain.usecase.GetTransactionsUseCase
import com.mvnh.feature_finances.domain.usecase.TransferTransactionUseCase
import com.mvnh.feature_finances.domain.usecase.WithdrawTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class FinancesViewModel(
    private val getSavingsInfoUseCase: GetSavingsInfoUseCase,
    private val addGoalUseCase: AddGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val transferUseCase: TransferTransactionUseCase,
    private val depositUseCase: DepositTransactionUseCase,
    private val withdrawUseCase: WithdrawTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _savingsInfo = MutableStateFlow<SavingsState>(SavingsState.Idle)
    val savingsInfo: StateFlow<SavingsState> get() = _savingsInfo

    private val _goals = MutableStateFlow<GoalsState>(GoalsState.Idle)
    val goals: StateFlow<GoalsState> get() = _goals

    private val _transactions = MutableStateFlow<TransactionsState>(TransactionsState.Idle)
    val transactions: StateFlow<TransactionsState> get() = _transactions

    init {
        loadInfo()
    }

    fun addGoal(
        name: String,
        target: Int,
        tillDate: Long
    ) {
        viewModelScope.launch {
            _goals.value = GoalsState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    addGoalUseCase(name, target, tillDate)
                }
            }.fold(
                onSuccess = { goalsState ->
                    _goals.value = goalsState.getOrNull()
                        ?: GoalsState.Error("Failed to add goal")

                    loadInfo()
                },
                onFailure = { e ->
                    _goals.value =
                        GoalsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun deleteGoal(goalId: Int) {
        viewModelScope.launch {
            _goals.value = GoalsState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    deleteGoalUseCase(goalId)
                }
            }.fold(
                onSuccess = { goalsState ->
                    _goals.value = goalsState.getOrNull()
                        ?: GoalsState.Error("Failed to delete goal")

                    loadInfo()
                },
                onFailure = { e ->
                    _goals.value =
                        GoalsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun transfer(
        fromGoalId: Int,
        toGoalId: Int,
        amount: Int,
        comment: String
    ) {
        viewModelScope.launch {
            _transactions.value = TransactionsState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    transferUseCase(fromGoalId, toGoalId, amount, comment)
                }
            }.fold(
                onSuccess = { transactionsState ->
                    _transactions.value = transactionsState.getOrNull()
                        ?: TransactionsState.Error("Failed to transfer")

                    loadInfo()
                },
                onFailure = { e ->
                    _transactions.value =
                        TransactionsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun deposit(
        goalId: Int,
        amount: Int,
        comment: String
    ) {
        viewModelScope.launch {
            _transactions.value = TransactionsState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    depositUseCase(goalId, amount, comment)
                }
            }.fold(
                onSuccess = { transactionsState ->
                    _transactions.value = transactionsState.getOrNull()
                        ?: TransactionsState.Error("Failed to deposit")

                    loadInfo()
                },
                onFailure = { e ->
                    _transactions.value =
                        TransactionsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun withdraw(
        goalId: Int,
        amount: Int,
        comment: String
    ) {
        viewModelScope.launch {
            _transactions.value = TransactionsState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    withdrawUseCase(goalId, amount, comment)
                }
            }.fold(
                onSuccess = { transactionsState ->
                    _transactions.value = transactionsState.getOrNull()
                        ?: TransactionsState.Error("Failed to withdraw")

                    loadInfo()
                },
                onFailure = { e ->
                    _transactions.value =
                        TransactionsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    private fun loadInfo() {
        viewModelScope.launch {
            val savingsInfoDeferred = async { loadSavingsInfo() }
            val goalsDeferred = async { loadGoals() }
            val transactionsDeferred = async { loadTransactions() }

            awaitAll(savingsInfoDeferred, goalsDeferred, transactionsDeferred)
        }
    }

    private fun loadSavingsInfo() {
        viewModelScope.launch {
            _savingsInfo.value = SavingsState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getSavingsInfoUseCase() }
            }.fold(
                onSuccess = { savingsState ->
                    _savingsInfo.value = savingsState.getOrNull()
                        ?: SavingsState.Error("Failed to get savings info")
                },
                onFailure = { e ->
                    _savingsInfo.value =
                        SavingsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    private fun loadGoals() {
        viewModelScope.launch {
            _goals.value = GoalsState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getGoalsUseCase() }
            }.fold(
                onSuccess = { goalsState ->
                    _goals.value = goalsState.getOrNull()
                        ?: GoalsState.Error("Failed to get goals")
                },
                onFailure = { e ->
                    _goals.value =
                        GoalsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _transactions.value = TransactionsState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getTransactionsUseCase() }
            }.fold(
                onSuccess = { transactionsState ->
                    _transactions.value = transactionsState.getOrNull()
                        ?: TransactionsState.Error("Failed to get transactions")
                },
                onFailure = { e ->
                    _transactions.value =
                        TransactionsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}
