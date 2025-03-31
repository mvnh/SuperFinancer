package com.mvnh.feature_finances.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mvnh.feature_finances.ui.screen.FinancesScreen
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo

fun NavGraphBuilder.financesScreen(onNavigateTo: OnNavigateTo) {
    composable<NavigableRoute.Finances> {
        FinancesScreen()
    }
}
