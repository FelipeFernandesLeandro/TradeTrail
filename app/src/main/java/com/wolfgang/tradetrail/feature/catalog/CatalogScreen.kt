package com.wolfgang.tradetrail.feature.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.designsystem.component.TTAppBar
import com.wolfgang.tradetrail.feature.product_detail.ui.ProductCard

@Composable
fun CatalogScreen(
    vm: CatalogViewModel,
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit
) {
    val products = vm.products.collectAsLazyPagingItems()
    val cartCount by vm.cartCount.collectAsState(0)
    val uiState = vm.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { message ->
            snackbarHostState.showSnackbar(message)
            vm.errorShown()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TTAppBar("Catalog",
                cartCount = cartCount,
                onCartClick = onCartClick,
                onBack = null
            )
         },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val refreshState = products.loadState.refresh
            when (refreshState) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is LoadState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Oops! Failed to load products.\nCheck your internet connection and try again.",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { products.retry() }
                        ) {
                            Text("Try again")
                        }
                    }
                }
                is LoadState.NotLoading -> {
                    if (products.itemCount == 0) {
                        Text(
                            "No products found",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    } else {
                        LazyVerticalGrid(
                            GridCells.Fixed(2),
                            Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            items(products.itemCount) { index ->
                                products[index]?.let { product ->
                                    ProductCard(
                                        product,
                                        onAddToCart = vm::addToCart,
                                        onClick = onProductClick
                                    )
                                }
                            }

                            products.loadState.append.let {
                                when (it) {
                                    is LoadState.Loading -> {
                                        item {
                                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                                CircularProgressIndicator(
                                                    Modifier.align(Alignment.Center).size(24.dp)
                                                )
                                            }
                                        }
                                    }

                                    is LoadState.Error -> {
                                        item {
                                            Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text("Failed to load more products", color = MaterialTheme.colorScheme.error)
                                                Button(onClick = { products.retry() }) {
                                                    Text("Try again")
                                                }
                                            }
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}