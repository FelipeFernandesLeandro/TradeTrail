package com.wolfgang.tradetrail.core.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalog : Screen("catalog")
    object Checkout : Screen("checkout")
}