package com.vitesse.hr.presentation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen("home")

    data object Details : Screen(
        route = "details/{id}",
        navArguments = listOf(navArgument("id") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(id: String) = "details/$id"
    }

    data object Edit : Screen(
            route = "edit/{id}",
        navArguments = listOf(navArgument("id") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(id: String) = "edit/$id"
    }
}
