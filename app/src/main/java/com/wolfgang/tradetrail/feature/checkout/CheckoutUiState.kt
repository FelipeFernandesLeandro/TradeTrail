package com.wolfgang.tradetrail.feature.checkout

import com.wolfgang.tradetrail.core.data.model.Product

data class CheckoutUiState(
    val items: List<CartItemUi> = emptyList(),
    val total: Double = .0,
    val discountedTotal: Double = .0,
    val totalQuantity: Int = 0,
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

data class CartItemUi(
    val productId: Int,
    val title: String,
    val thumbnail: String,
    val price: Double,
    val quantity: Int
)

fun Product.toUi() = CartItemUi(id, title, thumbnail, price, quantity)
