package com.mvnh.feature_home.domain.model

internal data class Ticker(
    val ticker: String,
    val logoUrl: String,
    val currentPrice: Double,
    val changePercent: Double,
    val priceChangeType: PriceChangeType,
)
