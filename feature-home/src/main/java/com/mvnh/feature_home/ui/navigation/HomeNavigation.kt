package com.mvnh.feature_home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.mvnh.feature_home.ui.screen.ArticleScreen
import com.mvnh.feature_home.ui.screen.HomeScreen
import com.mvnh.navigation.NavigableGraph
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo

fun NavGraphBuilder.mainScreens(onNavigateTo: OnNavigateTo) =
    navigation<NavigableGraph.Main>(
        startDestination = NavigableRoute.Main.Home
    ) {
        composable<NavigableRoute.Main.Home> {
            HomeScreen(onNavigateTo)
        }
        composable<NavigableRoute.Main.Article> { backStackEntry ->
            val articleData: NavigableRoute.Main.Article = backStackEntry.toRoute()
            ArticleScreen(
                title = articleData.title,
                url = articleData.url,
                onNavigateTo = onNavigateTo
            )
        }
    }
