package com.mvnh.superfinancer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mvnh.superfinancer.ui.navigation.AppNavBar
import com.mvnh.superfinancer.ui.navigation.AppNavHost

@Composable
internal fun RootScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            AppNavBar(navController = navController)
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppNavHost(navController = navController)
        }
    }
}
