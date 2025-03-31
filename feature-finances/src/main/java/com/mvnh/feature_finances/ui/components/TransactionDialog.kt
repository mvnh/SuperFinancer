package com.mvnh.feature_finances.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mvnh.feature_finances.R
import com.mvnh.feature_finances.domain.model.Goal
import com.mvnh.feature_finances.domain.model.TransactionType

@Composable
internal fun TransactionDialog(
    transactionType: TransactionType,
    goal: Goal,
    goals: List<Goal>,
    onDismiss: () -> Unit,
    onConfirm: (fromGoalId: Int, toGoalId: Int, amount: Int, comment: String) -> Unit
) {
    val context = LocalContext.current
    var amount by rememberSaveable { mutableStateOf("") }
    var comment by rememberSaveable { mutableStateOf("") }
    var selectedGoalId by rememberSaveable { mutableStateOf<Int?>(null) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    fun validateTransaction(): Boolean {
        if (amount.isEmpty()) {
            errorMessage = null
            return false
        }
        val amountValue = amount.toIntOrNull() ?: return false.also {
            errorMessage = context.getString(R.string.invalid_amount)
        }
        val fromGoal = goals.find { it.id == goal.id } ?: return false
        val toGoal = goals.find { it.id == selectedGoalId }

        return when (transactionType) {
            TransactionType.TRANSFER -> {
                when {
                    selectedGoalId == null -> {
                        errorMessage = context.getString(R.string.select_goal)
                        false
                    }
                    amountValue <= 0 -> {
                        errorMessage = context.getString(R.string.invalid_amount)
                        false
                    }
                    amountValue > fromGoal.currentAmount -> {
                        errorMessage = context.getString(R.string.not_enough_funds)
                        false
                    }
                    toGoal != null && toGoal.currentAmount + amountValue > toGoal.targetAmount -> {
                        amount = (toGoal.targetAmount - toGoal.currentAmount).toString()
                        errorMessage = context.getString(R.string.target_exceeded)
                        false
                    }
                    else -> {
                        errorMessage = null
                        true
                    }
                }
            }
            TransactionType.DEPOSIT -> {
                when {
                    amountValue > fromGoal.targetAmount - fromGoal.currentAmount -> {
                        errorMessage = context.getString(R.string.target_exceeded)
                        false
                    }
                    amountValue <= 0 -> {
                        errorMessage = context.getString(R.string.invalid_amount)
                        false
                    }
                    else -> {
                        errorMessage = null
                        true
                    }
                }
            }
            TransactionType.WITHDRAW -> {
                when {
                    amountValue > fromGoal.currentAmount -> {
                        errorMessage = context.getString(R.string.not_enough_funds)
                        false
                    }
                    amountValue <= 0 -> {
                        errorMessage = context.getString(R.string.invalid_amount)
                        false
                    }
                    else -> {
                        errorMessage = null
                        true
                    }
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (validateTransaction()) {
                        val fromGoalId = goal.id
                        val toGoalId = selectedGoalId ?: goal.id
                        onConfirm(fromGoalId, toGoalId, amount.toInt(), comment)
                        onDismiss()
                    }
                },
                enabled = validateTransaction()
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        title = {
            Text(
                text = when (transactionType) {
                    TransactionType.TRANSFER -> stringResource(id = R.string.transfer)
                    TransactionType.DEPOSIT -> stringResource(id = R.string.deposit)
                    TransactionType.WITHDRAW -> stringResource(id = R.string.withdraw)
                }
            )
        },
        text = {
            Column {
                Text(
                    text = when (transactionType) {
                        TransactionType.DEPOSIT -> "${stringResource(id = R.string.to)}: ${goal.name}"
                        else -> "${stringResource(id = R.string.from)}: ${goal.name}"
                    }
                )

                if (transactionType == TransactionType.TRANSFER) {
                    DropdownMenuGoalSelector(
                        goals = goals.filter { it.id != goal.id },
                        selectedGoalId = selectedGoalId,
                        onGoalSelected = { selectedGoalId = it }
                    )
                }

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        validateTransaction()
                    },
                    label = { Text(text = stringResource(id = R.string.amount)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    isError = errorMessage != null,
                    supportingText = {
                        errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
                    }
                )

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text(text = stringResource(id = R.string.comment)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
        }
    )
}
