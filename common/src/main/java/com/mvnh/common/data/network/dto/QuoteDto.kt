package com.mvnh.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(
    @SerialName("c") val currentPrice: Double,
    @SerialName("d") val change: Double?,
    @SerialName("dp") val changePercent: Double?,
)
