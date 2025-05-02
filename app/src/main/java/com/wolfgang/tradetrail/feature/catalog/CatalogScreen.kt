package com.wolfgang.tradetrail.feature.catalog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.designsystem.component.TTAppBar
import com.wolfgang.tradetrail.feature.catalog.ui.ProductCard
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue

@Composable
fun CatalogScreen(
    vm: CatalogViewModel,
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit
) {
    val products = vm.products.collectAsLazyPagingItems()
    val cartCount by vm.cartCount.collectAsState(0)

    Scaffold(
        topBar = {
            TTAppBar("Catalog",
                cartCount = cartCount,
                onCartClick = onCartClick,
                onBack = null
            )
         }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(products.itemCount) { index ->
                val product = products[index]
                if (product != null) {
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