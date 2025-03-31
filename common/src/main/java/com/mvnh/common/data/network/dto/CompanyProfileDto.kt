package com.mvnh.common.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompanyProfileDto(
    val ticker: String,
    val logo: String,
)
