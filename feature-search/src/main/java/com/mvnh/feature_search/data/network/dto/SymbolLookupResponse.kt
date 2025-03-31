package com.mvnh.feature_search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class SymbolLookupResponse(
    val result: List<SymbolLookupDto>
)

@Serializable
internal data class SymbolLookupDto(
    val symbol: String
)
