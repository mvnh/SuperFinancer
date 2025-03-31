package com.mvnh.superfinancer.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mvnh.navigation.NavigableRoute
import com.mvnh.superfinancer.R
import com.mvnh.superfinancer.ui.navigation.AppNavHost

@Composable
internal fun RootScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val currentScreen = items.find {
        currentDestination?.hasRoute(it::class) == true
    }
    val isBottomBarVisible = currentScreen in items

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            items.forEach { item ->
                item(
                    selected = item == currentScreen,
                    onClick = {
                        navController.navigate(item) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(item.label)
                        )
                    }
                )
            }
        },
        modifier = modifier,
        layoutType = if (isBottomBarVisible) NavigationSuiteType.NavigationDrawer else NavigationSuiteType.None
    ) {
        AppNavHost(navController)
    }
}

private val items = listOf(
    NavigableRoute.Main.Home,
    NavigableRoute.Finances,
    NavigableRoute.Feed.Posts
)

private val NavigableRoute.icon
    get() =
        when (this) {
            is NavigableRoute.Main.Home -> Icons.Default.Home
            is NavigableRoute.Finances -> Icons.Default.Money
            is NavigableRoute.Feed.Posts -> Icons.Default.Home
            else -> Icons.Default.Home
        }

private val NavigableRoute.label
    get() =
        when (this) {
            is NavigableRoute.Main.Home -> R.string.main
            is NavigableRoute.Finances -> R.string.finances
            is NavigableRoute.Feed.Posts -> R.string.feed
            else -> R.string.main
        }