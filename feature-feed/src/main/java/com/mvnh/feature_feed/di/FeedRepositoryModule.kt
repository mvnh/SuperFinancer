package com.mvnh.feature_feed.di

import com.mvnh.feature_feed.data.repository.IPostsRepository
import com.mvnh.feature_feed.domain.repository.PostsRepository
import org.koin.dsl.module

val feedRepositoryModule = module {
    factory<PostsRepository> { IPostsRepository(get()) }
}
