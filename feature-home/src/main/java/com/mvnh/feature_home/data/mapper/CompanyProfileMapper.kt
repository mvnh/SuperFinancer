package com.mvnh.feature_home.data.mapper

import com.mvnh.common.data.network.dto.CompanyProfileDto
import com.mvnh.feature_home.domain.model.CompanyProfile

internal fun CompanyProfileDto.toModel() = CompanyProfile(
    ticker = ticker,
    logoUrl = logo
)
