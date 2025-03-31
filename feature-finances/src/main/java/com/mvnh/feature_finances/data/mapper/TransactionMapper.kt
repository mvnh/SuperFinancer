package com.mvnh.feature_finances.data.mapper

import com.mvnh.common.util.formatDate
import com.mvnh.feature_finances.data.local.entity.TransactionEntity
import com.mvnh.feature_finances.domain.model.DepositTransaction
import com.mvnh.feature_finances.domain.model.Goal
import com.mvnh.feature_finances.domain.model.Transaction
import com.mvnh.feature_finances.domain.model.TransactionType
import com.mvnh.feature_finances.domain.model.TransferTransaction
import com.mvnh.feature_finances.domain.model.WithdrawTransaction

internal fun TransactionEntity.toModel(goals: List<Goal>) = Transaction(
    type = TransactionType.entries.first { type -> type.id == this.type },
    fromGoalName = if (this.fromGoalId == -1) {
        ""
    } else {
        goals.firstOrNull { goal -> goal.id == this.fromGoalId }?.name ?: ""
    },
    toGoalName = if (this.toGoalId == -1) {
        ""
    } else {
        goals.firstOrNull { goal -> goal.id == this.toGoalId }?.name ?: ""
    },
    amount = this.amount,
    date = formatDate(this.timestamp, withoutYear = true),
    comment = this.comment,
    timestamp = this.timestamp
)

internal fun TransferTransaction.toEntity() = TransactionEntity(
    type = TransactionType.TRANSFER.id,
    fromGoalId = this.fromGoalId,
    toGoalId = this.toGoalId,
    amount = this.amount,
    comment = this.comment
)

internal fun DepositTransaction.toEntity() = TransactionEntity(
    type = TransactionType.DEPOSIT.id,
    toGoalId = this.goalId,
    amount = this.amount,
    comment = this.comment
)

internal fun WithdrawTransaction.toEntity() = TransactionEntity(
    type = TransactionType.WITHDRAW.id,
    fromGoalId = this.goalId,
    amount = this.amount,
    comment = this.comment
)
