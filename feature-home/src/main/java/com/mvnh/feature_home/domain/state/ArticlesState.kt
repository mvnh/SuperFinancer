package com.mvnh.feature_home.domain.state

import com.mvnh.feature_home.domain.model.Article

internal sealed class ArticlesState {
    data object Idle : ArticlesState()
    data object Loading : ArticlesState()
    data class GetArticlesSuccess(val articles: List<Article>) : ArticlesState()
    data class Error(val message: String) : ArticlesState()
}
