package com.wolfgang.tradetrail.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TTAppBar(
    title: String,
    onBack: (() -> Unit)? = null,
    cartCount: Int = 0,
    onCartClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            onBack?.let { IconButton(onClick = it) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "back") } }
        },
        actions = {
            BadgedBox(badge = { if (cartCount > 0) Badge { Text("$cartCount") } }) {
                IconButton(onClick = onCartClick) { Icon(Icons.Default.ShoppingCart, "cart") }
            }
        }
    )
}