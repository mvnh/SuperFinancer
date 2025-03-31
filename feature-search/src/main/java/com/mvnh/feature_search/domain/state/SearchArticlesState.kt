package com.mvnh.feature_search.domain.state

import com.mvnh.feature_search.domain.model.SearchArticle

internal sealed class SearchArticlesState {
    data object Idle : SearchArticlesState()
    data class Loading(val query: String) : SearchArticlesState()
    data class SearchArticlesSuccess(val articles: List<SearchArticle>) : SearchArticlesState()
    data class SearchArticlesError(val message: String) : SearchArticlesState()
}
