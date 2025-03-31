package com.mvnh.navigation

import kotlinx.serialization.Serializable

sealed class NavigableGraph : Navigable {

    @Serializable data object Main : NavigableGraph()

    @Serializable data object Feed : NavigableGraph()
}
