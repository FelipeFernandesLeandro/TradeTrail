package com.wolfgang.tradetrail.feature.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wolfgang.tradetrail.R
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.feature.checkout.formatted

@Composable
fun ProductCard(
    product: Product,
    onAddToCart: (Product) -> Unit = {},
    onClick: (Product) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(product) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box {
            SubcomposeAsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            ) {
                val painterState = this.painter.state
                when (painterState) {
                    is AsyncImagePainter.State.Loading -> {
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
                        LottieAnimation(
                            composition = composition,
                            modifier    = Modifier.fillMaxWidth()
                        )
                    }
                    is AsyncImagePainter.State.Error -> {
                        Box(
                            Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }

            IconButton(
                onClick = { onAddToCart(product) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(32.dp)
                    .size(48.dp)
                    .background(
                        color  = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape  = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(32.dp)
                )
            }

            Surface(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                tonalElevation  = 2.dp,
                shape           = RoundedCornerShape(12.dp),
                modifier        = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(
                        text      = product.title,
                        maxLines  = 1,
                        style     = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = product.category,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = product.price.formatted(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}