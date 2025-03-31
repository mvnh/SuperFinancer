package com.mvnh.feature_feed.di

import com.mvnh.feature_feed.domain.usecase.CreatePostUseCase
import com.mvnh.feature_feed.domain.usecase.DeletePostUseCase
import com.mvnh.feature_feed.domain.usecase.GetPostsUseCase
import org.koin.dsl.module

val feedUseCaseModule = module {
    factory { CreatePostUseCase(get()) }
    factory { GetPostsUseCase(get()) }
    factory { DeletePostUseCase(get()) }
}
