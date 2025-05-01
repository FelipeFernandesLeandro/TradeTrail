package com.wolfgang.tradetrail.feature.catalog

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.feature.catalog.ui.ProductCard

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