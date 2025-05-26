package com.wolfgang.tradetrail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wolfgang.tradetrail.core.designsystem.TradeTrailTheme
import com.wolfgang.tradetrail.feature.auth.LoginScreen
import com.wolfgang.tradetrail.feature.auth.LoginViewModel
import com.wolfgang.tradetrail.feature.catalog.CatalogScreen
import com.wolfgang.tradetrail.feature.catalog.CatalogViewModel
import com.wolfgang.tradetrail.feature.catalog.ProductDetailScreen
import com.wolfgang.tradetrail.feature.checkout.CheckoutScreen
import com.wolfgang.tradetrail.navigation.NavKeys
import com.wolfgang.tradetrail.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TradeTrailTheme(useDarkTheme = true) {
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
                                navController.navigate(Screen.ProductDetail.route(product.id))
                            },
                            onCartClick = { navController.navigate(Screen.Checkout.route) }
                        )
                    }
                    composable(Screen.Checkout.route) {
                        CheckoutScreen(
                            onBack = navController::popBackStack,
                            onPay = {
                                navController.popBackStack(Screen.Catalog.route, false)
                            },
                            onProductClick = { productId ->
                                navController.navigate(Screen.ProductDetail.route(productId))
                            }
                    ) }
                    composable(
                        Screen.ProductDetail.route,
                        listOf(navArgument(NavKeys.PRODUCT_ID) { type = NavType.IntType })
                    ) {
                        ProductDetailScreen(
                            onBack = navController::popBackStack,
                            onCartClick = { navController.navigate(Screen.Checkout.route) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PayoutScreen() {
    /* cart summary */
}
