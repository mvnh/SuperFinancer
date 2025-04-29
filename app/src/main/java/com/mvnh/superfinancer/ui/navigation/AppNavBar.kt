package com.mvnh.superfinancer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mvnh.navigation.NavigableRoute
import com.mvnh.superfinancer.R

@Composable
fun AppNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val currentScreen = destinations.find {
        currentDestination?.hasRoute(it::class) == true
    }
    val isBottomBarVisible = currentScreen != null

    if (!isBottomBarVisible) return

    NavigationBar {
        destinations.forEach { destination ->
            val isSelected = destination == currentScreen

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (isSelected) return@NavigationBarItem

                    navController.navigate(destination) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon(destination),
                        contentDescription = stringResource(id = label(destination))
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = label(destination))
                    )
                }
            )
        }
    }
}

private val destinations = listOf(
    NavigableRoute.Main.Home,
    NavigableRoute.Finances,
    NavigableRoute.Feed.Posts
)

private val icon: (NavigableRoute) -> ImageVector
    get() = { destination ->
        when (destination) {
            is NavigableRoute.Main.Home -> Icons.Filled.Home
            is NavigableRoute.Finances -> Icons.Filled.MonetizationOn
            is NavigableRoute.Feed.Posts -> Icons.AutoMirrored.Filled.Feed
            else -> Icons.Filled.Home
        }
    }

private val label: (NavigableRoute) -> Int
    get() = { destination ->
        when (destination) {
            is NavigableRoute.Main.Home -> R.string.home
            is NavigableRoute.Finances -> R.string.finances
            is NavigableRoute.Feed.Posts -> R.string.feed
            else -> 0
        }
    }
