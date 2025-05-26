package com.wolfgang.tradetrail.feature.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.designsystem.component.TTAppBar
import com.wolfgang.tradetrail.feature.catalog.ui.ProductCard

@Composable
fun CatalogScreen(
    vm: CatalogViewModel,
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit
) {
    val products = vm.products.collectAsLazyPagingItems()
    val cartCount by vm.cartCount.collectAsState(0)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TTAppBar("Catalog",
                cartCount = cartCount,
                onCartClick = onCartClick,
                onBack = null
            )
         }
    ) { innerPadding ->
        LazyVerticalGrid(
            GridCells.Fixed(2),
            Modifier
                .padding(innerPadding)
                .background(color = MaterialTheme.colorScheme.background),
            contentPadding     = PaddingValues(
                horizontal = 24.dp,
                vertical   = 24.dp
            ),
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
        }
    }
}