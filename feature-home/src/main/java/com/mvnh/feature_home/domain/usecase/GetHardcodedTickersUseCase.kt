package com.mvnh.feature_home.domain.usecase

import com.mvnh.feature_home.domain.repository.MarketRepository

internal class GetHardcodedTickersUseCase(private val repository: MarketRepository) {
    suspend operator fun invoke(): List<String> = repository.getHardcodedTickers()
}
