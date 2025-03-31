package com.mvnh.feature_finances.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mvnh.feature_finances.R
import com.mvnh.feature_finances.domain.model.Transaction
import com.mvnh.feature_finances.domain.model.TransactionType

private fun TransactionType.toIcon() = when (this) {
    TransactionType.TRANSFER -> Icons.Default.Refresh
    TransactionType.DEPOSIT ->  Icons.AutoMirrored.Filled.ArrowForward
    TransactionType.WITHDRAW -> Icons.AutoMirrored.Filled.ArrowBack
}

private fun TransactionType.toSymbol() = when (this) {
    TransactionType.TRANSFER -> ""
    TransactionType.DEPOSIT -> "+"
    TransactionType.WITHDRAW -> "-"
}

private fun TransactionType.toColor() = when (this) {
    TransactionType.TRANSFER -> Color.Gray
    TransactionType.DEPOSIT -> Color.Green
    TransactionType.WITHDRAW -> Color.Red
}

private fun TransactionType.toTextType() = when (this) {
    TransactionType.TRANSFER -> R.string.transfer
    TransactionType.DEPOSIT -> R.string.deposit
    TransactionType.WITHDRAW -> R.string.withdraw
}

private fun Transaction.getGoalText() = when (this.type) {
    TransactionType.TRANSFER -> "${this.fromGoalName} -> ${this.toGoalName}"
    TransactionType.DEPOSIT -> this.toGoalName
    TransactionType.WITHDRAW -> this.fromGoalName
}

@Composable
internal fun TransactionCard(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = transaction.type.toIcon(),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                )
                Column {
                    Text(
                        text = stringResource(id = transaction.type.toTextType()),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = transaction.getGoalText(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${transaction.type.toSymbol()} ${transaction.amount} ₽",
                    style = MaterialTheme.typography.titleMedium,
                    color = transaction.type.toColor()
                )
            }
            if (transaction.comment.isNotBlank()) {
                Text(
                    text = transaction.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}
