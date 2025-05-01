package com.wolfgang.tradetrail.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
	val total: Int,
	val carts: List<Cart>,
	val limit: Int,
	val skip: Int
)

@Serializable
data class Cart(
	val discountedTotal: Double,
	val total: Double,
	val totalQuantity: Int,
	val totalProducts: Int,
	val id: Int,
	val userId: Int,
	val products: List<Product>
)

data class CartBody(
	val userId: Int,
	val products: List<ProductReference>
)


data class UpdateCartBody(
	val userId: Int,
	val products: List<ProductReference>,
	val merge: Boolean = true
)

data class ProductReference(
	val id: Int,
	val quantity: Int
)
