package com.wolfgang.tradetrail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wolfgang.tradetrail.core.designsystem.TradeTrailTheme
import com.wolfgang.tradetrail.feature.auth.LoginScreen
import com.wolfgang.tradetrail.feature.auth.LoginViewModel
import com.wolfgang.tradetrail.feature.catalog.CatalogScreen
import com.wolfgang.tradetrail.feature.catalog.CatalogViewModel
import com.wolfgang.tradetrail.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TradeTrailTheme {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    startDestination = Screen.Login.route
                ) {
                    composable(Screen.Login.route) {
                        val vm: LoginViewModel = hiltViewModel()
                        LoginScreen(vm) {
                            navController.navigate(Screen.Catalog.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }
                    composable(Screen.Catalog.route) {
                        val vm: CatalogViewModel = hiltViewModel()
                        CatalogScreen(
                            vm,
                            onProductClick = { product ->
                                // TODO: add to cart / save it on SavedStateHandle
                                navController.navigate(Screen.Checkout.route)
                            }
                        )
                    }
                    composable(Screen.Checkout.route) { CheckoutScreen() }
                }
            }
        }
    }
}

@Composable
fun CheckoutScreen() {
    /* cart summary */
}
