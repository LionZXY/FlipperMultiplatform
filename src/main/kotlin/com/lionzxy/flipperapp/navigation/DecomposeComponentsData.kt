package com.lionzxy.flipperapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class DecomposeComponentsData {
    @Serializable
    data object MainScreen : DecomposeComponentsData()
}