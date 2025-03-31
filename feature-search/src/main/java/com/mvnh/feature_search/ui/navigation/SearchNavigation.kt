package com.mvnh.feature_search.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mvnh.feature_search.ui.screen.SearchScreen
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo

fun NavGraphBuilder.searchScreen(onNavigateTo: OnNavigateTo) {
    composable<NavigableRoute.Search> {
        SearchScreen(onNavigateTo)
    }
}
