package com.wolfgang.tradetrail.feature.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.designsystem.component.TTAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    vm: ProductDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onCartClick: () -> Unit
) {
    val state = vm.uiState
    val cartCount by vm.cartCount.collectAsState(0)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope: CoroutineScope = rememberCoroutineScope()

    SnackbarHost(hostState = snackbarHostState)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TTAppBar(
                title = state.product?.title ?: "Details",
                onBack = onBack,
                cartCount = cartCount,
                onCartClick = onCartClick
            )
        }
    ) { padding ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
            state.product != null -> Column(
                Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = state.product.thumbnail,
                    contentDescription = state.product.title,
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(16.dp))
                Text(state.product.title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text(state.product.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                AddToCartButton(
                    vm = vm,
                    product = state.product,
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )
            }
        }
    }
}

@Composable
fun AddToCartButton(
    vm: ProductDetailViewModel,
    product: Product,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    Button(
        onClick = {
            vm.addToCart(product)
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Added to cart",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true,
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Add to cart")
    }
}
