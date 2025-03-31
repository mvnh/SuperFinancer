package com.mvnh.feature_search.data.mapper

import com.mvnh.feature_search.data.network.dto.SymbolLookupDto
import com.mvnh.feature_search.domain.model.SearchTicker

internal fun SymbolLookupDto.toSearchTicker(price: Double) = SearchTicker(
    symbol = symbol,
    price = price
)
