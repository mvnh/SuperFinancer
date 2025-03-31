@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_finances.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvnh.common.ui.components.ContentSection
import com.mvnh.feature_finances.R
import com.mvnh.feature_finances.domain.model.Goal
import com.mvnh.feature_finances.domain.model.SavingsInfo
import com.mvnh.feature_finances.domain.model.Transaction
import com.mvnh.feature_finances.domain.model.TransactionType
import com.mvnh.feature_finances.domain.state.GoalsState
import com.mvnh.feature_finances.domain.state.SavingsState
import com.mvnh.feature_finances.domain.state.TransactionsState
import com.mvnh.feature_finances.ui.components.AllSavingsCard
import com.mvnh.feature_finances.ui.components.GoalCard
import com.mvnh.feature_finances.ui.components.TransactionCard
import com.mvnh.feature_finances.ui.components.TransactionDialog
import com.mvnh.feature_finances.ui.viewmodel.FinancesViewModel
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun FinancesScreen(viewModel: FinancesViewModel = koinViewModel()) {
    val savingsInfo by viewModel.savingsInfo.collectAsState()
    val goals by viewModel.goals.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    var selectedGoal by rememberSaveable { mutableStateOf<Goal?>(null) }
    var selectedTransactionType by rememberSaveable { mutableStateOf<TransactionType?>(null) }
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var target by rememberSaveable { mutableStateOf("") }
    var tillDate by rememberSaveable { mutableStateOf("") }
    var isDatePickerVisible by rememberSaveable { mutableStateOf(false) }
    val todayMillis = rememberSaveable { Instant.now().toEpochMilli() }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        }
    )

    fun onDateConfirmed(newDate: String) {
        tillDate = newDate
        isDatePickerVisible = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.finances),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { innerPadding ->
        Column(modifier = mainScreenModifier(innerPadding)) {
            ContentSection(
                title = stringResource(id = R.string.savings_info),
                isLoading = savingsInfo is SavingsState.Loading,
                content = { SavingsInfoContent(savingsInfo) }
            )
            ContentSection(
                title = stringResource(id = R.string.goals),
                isLoading = goals is GoalsState.Loading,
                trailingIcon = { AddIcon() },
                onTrailingIconClick = { isDialogVisible = true },
                content = {
                    GoalsContent(
                        state = goals,
                        onTransferClick = { goal ->
                            selectedTransactionType = TransactionType.TRANSFER
                            selectedGoal = goal
                        },
                        onDepositClick = { goal ->
                            selectedTransactionType = TransactionType.DEPOSIT
                            selectedGoal = goal
                        },
                        onWithdrawClick = { goal ->
                            selectedTransactionType = TransactionType.WITHDRAW
                            selectedGoal = goal
                        },
                        onDeleteClick = { goal ->
                            viewModel.deleteGoal(goal.id)
                        }
                    )
                }
            )
            ContentSection(
                title = stringResource(id = R.string.transactions),
                isLoading = transactions is TransactionsState.Loading,
                content = { TransactionsContent(transactions) }
            )
        }
    }

    if (isDialogVisible) {
        AddGoalDialog(
            onDismiss = {
                isDialogVisible = false
                name = ""
                target = ""
                tillDate = ""
            },
            onConfirm = { goalName, goalTarget, goalTillDate ->
                viewModel.addGoal(goalName, goalTarget, goalTillDate)
                name = ""
                target = ""
                tillDate = ""
            },
            name = name,
            target = target,
            tillDate = tillDate,
            onNameChange = { name = it },
            onTargetChange = { target = it },
            onTillDateChange = { tillDate = it },
            datePickerState = datePickerState,
            isDatePickerVisible = isDatePickerVisible,
            onDatePickerToggle = { isDatePickerVisible = !isDatePickerVisible },
            onDateConfirmed = ::onDateConfirmed
        )
    }

    selectedTransactionType?.let { transactionType ->
        selectedGoal?.let { goal ->
            TransactionDialog(
                transactionType = transactionType,
                goal = goal,
                goals = (goals as? GoalsState.GetGoalsSuccess)?.goals.orEmpty(),
                onDismiss = {
                    selectedTransactionType = null
                    selectedGoal = null
                },
                onConfirm = { fromGoalId, toGoalId, amount, comment ->
                    when (transactionType) {
                        TransactionType.TRANSFER -> viewModel.transfer(fromGoalId, toGoalId, amount, comment)
                        TransactionType.DEPOSIT -> viewModel.deposit(fromGoalId, amount, comment)
                        TransactionType.WITHDRAW -> viewModel.withdraw(fromGoalId, amount, comment)
                    }
                    selectedTransactionType = null
                    selectedGoal = null
                }
            )
        }
    }
}

@Composable
private fun SavingsInfoContent(state: SavingsState) {
    AnimatedVisibility(
        visible = state is SavingsState.GetSavingsInfoSuccess,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.animateContentSize()
    ) {
        (state as? SavingsState.GetSavingsInfoSuccess)?.let {
            SavingsInfoContainer(it.info)
        }
    }
}

@Composable
private fun SavingsInfoContainer(info: SavingsInfo) {
    Column(modifier = listModifier()) {
        AllSavingsCard(info)
    }
}

@Composable
private fun GoalsContent(
    state: GoalsState,
    onTransferClick: (Goal) -> Unit,
    onDepositClick: (Goal) -> Unit,
    onWithdrawClick: (Goal) -> Unit,
    onDeleteClick: (Goal) -> Unit
) {
    AnimatedVisibility(
        visible = state is GoalsState.GetGoalsSuccess,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.animateContentSize()
    ) {
        (state as? GoalsState.GetGoalsSuccess)?.let {
            GoalsList(
                goals = it.goals,
                onTransferClick = onTransferClick,
                onDepositClick = onDepositClick,
                onWithdrawClick = onWithdrawClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun GoalsList(
    goals: List<Goal>,
    onTransferClick: (Goal) -> Unit,
    onDepositClick: (Goal) -> Unit,
    onWithdrawClick: (Goal) -> Unit,
    onDeleteClick: (Goal) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = listModifier()
    ) {
        goals.forEach { goal ->
            GoalCard(
                goal = goal,
                onTransferClick = { onTransferClick(goal) },
                onDepositClick = { onDepositClick(goal) },
                onWithdrawClick = { onWithdrawClick(goal) },
                onDeleteClick = { onDeleteClick(goal) }
            )
        }
    }
}

@Composable
private fun AddGoalDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Long) -> Unit,
    name: String,
    target: String,
    tillDate: String,
    onNameChange: (String) -> Unit,
    onTargetChange: (String) -> Unit,
    onTillDateChange: (String) -> Unit,
    datePickerState: DatePickerState = rememberDatePickerState(),
    isDatePickerVisible: Boolean,
    onDatePickerToggle: () -> Unit,
    onDateConfirmed: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        name,
                        target.toIntOrNull() ?: 0,
                        datePickerState.selectedDateMillis ?: 0
                    )
                    onDismiss()
                },
                enabled = name.isNotBlank() && (target.toIntOrNull() ?: 0) > 0
            ) {
                Text(
                    text = stringResource(id = R.string.confirm)
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_goal)
            )
        },
        text = {
            Column {
                GoalNameInput(
                    name = name,
                    onNameChange = onNameChange
                )
                GoalTargetInput(
                    target = target,
                    onTargetChange = onTargetChange,
                    onNext = onDatePickerToggle
                )
                GoalTillDateInput(
                    tillDate = tillDate,
                    onTillDateChange = onTillDateChange,
                    onDatePickerToggle = onDatePickerToggle
                )
            }
        }
    )

    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { onDatePickerToggle() },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedFormattedDate = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        onDateConfirmed(selectedFormattedDate)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm)
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun GoalNameInput(
    name: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = {
            Text(
                text = stringResource(id = R.string.name)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}

@Composable
private fun GoalTargetInput(
    target: String,
    onTargetChange: (String) -> Unit,
    onNext: () -> Unit
) {
    OutlinedTextField(
        value = target,
        onValueChange = onTargetChange,
        label = {
            Text(
                text = stringResource(id = R.string.target_amount)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }
        )
    )
}

@Composable
private fun GoalTillDateInput(
    tillDate: String,
    onTillDateChange: (String) -> Unit,
    onDatePickerToggle: () -> Unit
) {
    Box {
        OutlinedTextField(
            value = tillDate,
            onValueChange = onTillDateChange,
            readOnly = true,
            label = {
                Text(
                    text = stringResource(id = R.string.till_date)
                )
            }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable { onDatePickerToggle() }
        )
    }
}

@Composable
private fun TransactionsContent(state: TransactionsState) {
    AnimatedVisibility(
        visible = state is TransactionsState.GetTransactionsSuccess,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.animateContentSize()
    ) {
        (state as? TransactionsState.GetTransactionsSuccess)?.let {
            TransactionsList(it.transactions)
        }
    }
}

@Composable
private fun TransactionsList(transactions: List<Transaction>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = listModifier()
    ) {
        transactions.forEach { transaction ->
            TransactionCard(transaction = transaction)
        }
    }
}

@Composable
private fun AddIcon() {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null
    )
}

@Composable
private fun mainScreenModifier(innerPadding: PaddingValues) = Modifier
    .fillMaxSize()
    .padding(innerPadding)
    .verticalScroll(rememberScrollState())

private fun listModifier() = Modifier
    .padding(
        start = 8.dp,
        end = 8.dp,
        bottom = 8.dp
    )
    .clip(RoundedCornerShape(16.dp))
