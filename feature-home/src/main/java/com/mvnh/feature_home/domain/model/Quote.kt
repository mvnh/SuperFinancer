package com.mvnh.feature_home.domain.model

internal data class Quote(
    val currentPrice: Double,
    val changePercent: Double,
    val priceChangeType: PriceChangeType,
)

internal enum class PriceChangeType {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}
