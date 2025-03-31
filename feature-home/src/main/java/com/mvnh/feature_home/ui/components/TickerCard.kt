package com.mvnh.feature_home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mvnh.feature_home.R
import com.mvnh.feature_home.domain.model.PriceChangeType
import com.mvnh.feature_home.domain.model.Ticker

@Composable
internal fun TickerCard(
    ticker: Ticker,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .width(300.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(ticker.logoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.ticker_logo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(60.dp)
                    .width(60.dp)
                    .clip(CircleShape)
            )
            Text(
                text = ticker.ticker,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))

            val arrowSymbol = when (ticker.priceChangeType) {
                PriceChangeType.POSITIVE -> "↑"
                PriceChangeType.NEGATIVE -> "↓"
                PriceChangeType.NEUTRAL -> ""
            }
            val priceText = String.format("%.2f", ticker.currentPrice) +
                    " (${String.format("%.2f", ticker.changePercent)}%) " +
                    arrowSymbol
            Text(
                text = priceText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 16.dp),
                color = when (ticker.priceChangeType) {
                    PriceChangeType.POSITIVE -> Color.Green
                    PriceChangeType.NEGATIVE -> Color.Red
                    PriceChangeType.NEUTRAL -> Color.Gray
                }
            )
        }
    }
}
