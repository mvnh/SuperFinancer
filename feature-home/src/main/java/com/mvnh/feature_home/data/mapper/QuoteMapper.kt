package com.mvnh.feature_home.data.mapper

import com.mvnh.common.data.network.dto.QuoteDto
import com.mvnh.feature_home.domain.model.PriceChangeType
import com.mvnh.feature_home.domain.model.Quote

internal fun QuoteDto.toModel(
    priceChangeType: PriceChangeType?
) = Quote(
    currentPrice = currentPrice,
    changePercent = changePercent ?: 0.0,
    priceChangeType = priceChangeType ?: PriceChangeType.NEUTRAL
)
