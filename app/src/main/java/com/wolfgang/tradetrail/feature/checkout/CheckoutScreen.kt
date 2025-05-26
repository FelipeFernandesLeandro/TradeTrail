package com.wolfgang.tradetrail.feature.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wolfgang.tradetrail.feature.checkout.ui.CartItemRow
import com.wolfgang.tradetrail.feature.checkout.ui.CheckoutAppBar
import com.wolfgang.tradetrail.feature.checkout.ui.OrderSummary
import com.wolfgang.tradetrail.feature.checkout.ui.paymentSuccessAnimation


@Composable
fun CheckoutScreen(
    vm: CheckoutViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onPay: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CheckoutAppBar(
                title = "Checkout",
                onBack = onBack,
                onClearClick = vm::clear
            )
        },
        bottomBar = {
            if (uiState.items.isNotEmpty() && !uiState.loading && uiState.error == null && !uiState.success) {
                OrderSummary(uiState.total, uiState.discountedTotal) {
                    vm.checkout()
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                uiState.loading -> Box(
                    Modifier.fillMaxSize(),
                    Alignment.Center
                ) { CircularProgressIndicator() }

                uiState.error != null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        uiState.error!!
                    )
                }

                uiState.success && !hasNavigated -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val progress = paymentSuccessAnimation()
                    if (progress == 1f) {
                        hasNavigated = true
                        onPay()
                        vm.resetCheckoutSuccess()
                    }
                }

                uiState.items.isEmpty() -> Box(
                    Modifier.fillMaxSize(),
                    Alignment.Center
                ) { Text("Your cart is empty") }

                else -> LazyColumn(Modifier.fillMaxSize()) {
                    items(uiState.items.size) { index ->
                        val item = uiState.items[index]
                        CartItemRow(
                            item,
                            onDelete = { vm.remove(item.productId) },
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}

fun Double.formatted() = "$ %.2f".format(this)
