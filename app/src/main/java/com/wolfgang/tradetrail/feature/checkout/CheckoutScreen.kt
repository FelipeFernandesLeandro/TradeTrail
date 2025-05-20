package com.wolfgang.tradetrail.feature.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wolfgang.tradetrail.core.designsystem.component.TTAppBar
import com.wolfgang.tradetrail.feature.checkout.ui.CartItemRow
import com.wolfgang.tradetrail.feature.checkout.ui.OrderSummary
import com.wolfgang.tradetrail.feature.checkout.ui.paymentSuccessAnimation


@Composable
fun CheckoutScreen(
    vm: CheckoutViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onPay: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TTAppBar(
                title = "Checkout",
                onBack = onBack,
                cartCount = uiState.totalQuantity,
                onCartClick = {}
            )
        }
    ) { padding ->
        when {
            uiState.loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
            uiState.error != null -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text(uiState.error!!) }
            uiState.success && !hasNavigated -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val progress = paymentSuccessAnimation()
                if (progress == 1f) {
                    hasNavigated = true
                    onPay()
                    vm.resetCheckoutSuccess()
                }
            }
            uiState.items.isEmpty() -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Your cart is empty") }
            else -> Column(Modifier.padding(padding)) {
                LazyColumn(Modifier.weight(1f)) {
                    items(uiState.items.size) { index ->
                        val item = uiState.items[index]
                        CartItemRow(item, onDelete = { vm.remove(item.productId) })
                    }
                }
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                OrderSummary(uiState.total, uiState.discountedTotal)
                Button(
                    onClick = {
                        vm.checkout()
                  },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) { Text("Confirm order (${uiState.total.formatted()})") }
            }
        }
    }
}

fun Double.formatted() = "$ %.2f".format(this)
