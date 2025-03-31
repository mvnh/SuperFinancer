package com.mvnh.navigation

import kotlinx.serialization.Serializable

interface Navigable

@Serializable
data object PreviousScreen : Navigable
