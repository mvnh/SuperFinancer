package com.mvnh.navigation

import kotlinx.serialization.Serializable

sealed class NavigableRoute : Navigable {

    sealed class Main : NavigableRoute() {

        @Serializable
        data object Home : Main()

        @Serializable
        data class Article(
            val title: String,
            val url: String
        ) : Main()
    }

    @Serializable
    data object Search : Main()

    @Serializable
    data object Finances : NavigableRoute()

    sealed class Feed : NavigableRoute() {

        @Serializable
        data object Posts : Feed()

        @Serializable
        data class CreatePost(
            val linkedArticleTitle: String? = null,
            val linkedArticleUrl: String? = null
        ) : Feed()
    }
}
