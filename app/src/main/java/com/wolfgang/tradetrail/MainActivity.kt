package com.wolfgang.tradetrail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.wolfgang.tradetrail.core.designsystem.TradeTrailTheme
import com.wolfgang.tradetrail.core.feature.catalog.CatalogViewModel
import com.wolfgang.tradetrail.core.navigation.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.feature.catalog.ui.ProductCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TradeTrailTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screen.Login.route) {
                    composable(Screen.Login.route) { LoginScreen { navController.navigate(Screen.Catalog.route) } }
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
fun LoginScreen(onLogin: () -> Unit) {
    /* form + button */
}

@Composable
fun CatalogScreen(vm: CatalogViewModel, onProductClick: (Product) -> Unit) {
    val products = vm.products.collectAsLazyPagingItems()

    LazyColumn {
        items(products.itemCount) { index ->
            val product = products[index]
            if (product != null) {
                ProductCard(product, onClick = onProductClick)
            }
        }
    }
}

@Composable
fun CheckoutScreen() {
    /* cart summary */
}
