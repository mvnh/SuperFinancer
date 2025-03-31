package com.mvnh.feature_finances.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvnh.feature_finances.R
import com.mvnh.feature_finances.domain.model.SavingsInfo

@Composable
internal fun AllSavingsCard(
    savingsInfo: SavingsInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.total),
                    modifier = Modifier.align(Alignment.TopStart)
                )
                Spacer(modifier = Modifier.padding(32.dp))
                Text(
                    text = "${savingsInfo.total} ₽",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.progress),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Spacer(modifier = Modifier.padding(32.dp))
                    Text(
                        text = "${savingsInfo.progressPercentage}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.avg_income),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Spacer(modifier = Modifier.padding(32.dp))
                    Text(
                        text = "${savingsInfo.avgIncome} ₽",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }
            }
        }
    }
}
