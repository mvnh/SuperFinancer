package com.mvnh.navigation

import androidx.navigation.NavOptionsBuilder

typealias OnNavigateTo = (Navigable, NavOptionsBuilder.() -> Unit) -> Unit
