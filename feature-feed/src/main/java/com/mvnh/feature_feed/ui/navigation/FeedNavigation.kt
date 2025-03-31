package com.mvnh.feature_feed.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.mvnh.feature_feed.ui.screen.CreatePostScreen
import com.mvnh.feature_feed.ui.screen.PostsScreen
import com.mvnh.navigation.NavigableGraph
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo

fun NavGraphBuilder.feedScreens(onNavigateTo: OnNavigateTo) =
    navigation<NavigableGraph.Feed>(
        startDestination = NavigableRoute.Feed.Posts
    ) {
        composable<NavigableRoute.Feed.Posts> {
            PostsScreen(onNavigateTo)
        }
        composable<NavigableRoute.Feed.CreatePost> { backStackEntry ->
            val data: NavigableRoute.Feed.CreatePost = backStackEntry.toRoute()
            CreatePostScreen(
                linkedArticleTitle = data.linkedArticleTitle,
                linkedArticleUrl = data.linkedArticleUrl,
                onNavigateTo = onNavigateTo
            )
        }
    }
