package com.mvnh.feature_finances.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvnh.feature_finances.R
import com.mvnh.feature_finances.domain.model.Goal

private fun Goal.getProgressColor(): Color {
    val progress = (currentAmount.toFloat() / targetAmount.toFloat()).coerceIn(0f, 1f)
    return when (progress) {
        in 0f..0.24f -> Color.Red
        in 0.25f..0.49f -> Color(0xFFE57373) // orange
        in 0.5f..0.74f -> Color.Yellow
        else -> Color.Green
    }
}

@Composable
internal fun GoalCard(
    goal: Goal,
    onTransferClick: (Goal) -> Unit,
    onDepositClick: (Goal) -> Unit,
    onWithdrawClick: (Goal) -> Unit,
    onDeleteClick: (Goal) -> Unit,
    modifier: Modifier = Modifier
) {
    var isGoalOptionsExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = { isGoalOptionsExpanded = true },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
            GoalOptionsDropdownMenu(
                expanded = isGoalOptionsExpanded,
                onDismissRequest = { isGoalOptionsExpanded = false },
                onTransferClick = {
                    isGoalOptionsExpanded = false
                    onTransferClick(goal)
                },
                onDepositClick = {
                    isGoalOptionsExpanded = false
                    onDepositClick(goal)
                },
                onWithdrawClick = {
                    isGoalOptionsExpanded = false
                    onWithdrawClick(goal)
                },
                onDeleteClick = {
                    isGoalOptionsExpanded = false
                    onDeleteClick(goal)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Column {
                Text(
                    text = "${goal.currentAmount}" +
                            " ${stringResource(id = R.string.out_of)} " +
                            "${goal.targetAmount} ₽",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = goal.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (goal.tillDate.isNotBlank()) {
                    Text(
                        text = "${stringResource(id = R.string.till)} ${goal.tillDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row {
                    val progress = (goal.currentAmount.toFloat() / goal.targetAmount.toFloat()).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { progress },
                        color = goal.getProgressColor(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun GoalOptionsDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onTransferClick: () -> Unit,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        TransferOption { onTransferClick() }
        DepositOption { onDepositClick() }
        WithdrawOption { onWithdrawClick() }
        DeleteOption { onDeleteClick() }
    }
}

@Composable
fun TransferOption(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(id = R.string.transfer)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )
        },
        onClick = onClick
    )
}

@Composable
fun DepositOption(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(id = R.string.deposit)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        },
        onClick = onClick
    )
}

@Composable
fun WithdrawOption(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(id = R.string.withdraw)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        },
        onClick = onClick
    )
}

@Composable
fun DeleteOption(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                text = stringResource(id = R.string.delete)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        },
        onClick = onClick
    )
}
