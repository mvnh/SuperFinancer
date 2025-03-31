package com.mvnh.superfinancer.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.mvnh.feature_feed.ui.navigation.feedScreens
import com.mvnh.feature_finances.ui.navigation.financesScreen
import com.mvnh.feature_home.ui.navigation.mainScreens
import com.mvnh.feature_search.ui.navigation.searchScreen
import com.mvnh.navigation.Navigable
import com.mvnh.navigation.NavigableGraph
import com.mvnh.navigation.PreviousScreen

@Composable
internal fun AppNavHost(
    navController: NavHostController,
    startDestination: NavigableGraph = NavigableGraph.Main
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        listOf(
            ::mainScreens,
            ::searchScreen,
            ::financesScreen,
            ::feedScreens
        ).forEach { screen ->
            screen { navigable, function ->
                navController.navigateTo(navigable, navOptions(function))
            }
        }
    }
}

private fun NavHostController.navigateTo(
    navigable: Navigable,
    function: NavOptions?,
) {
    when (navigable) {
        is PreviousScreen -> {
            val isBackStackEmpty = previousBackStackEntry == null
            if (!isBackStackEmpty) {
                popBackStack()
            }
        }
        else -> navigate(navigable, function)
    }
}
