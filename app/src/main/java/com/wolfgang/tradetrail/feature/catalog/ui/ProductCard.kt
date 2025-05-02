package com.wolfgang.tradetrail.feature.catalog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wolfgang.tradetrail.core.data.model.Product

@Composable
fun ProductCard(
    product: Product,
    onAddToCart: (Product) -> Unit = {},
    onClick: (Product) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(product) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(product.title, maxLines = 2)
            Text("${product.price}", style = MaterialTheme.typography.titleMedium)
            Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to cart")
            }
        }
    }
}