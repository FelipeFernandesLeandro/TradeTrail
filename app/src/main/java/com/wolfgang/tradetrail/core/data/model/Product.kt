package com.wolfgang.tradetrail.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val discountPercentage: Double,
    val discountedTotal: Double,
    val total: Double,
    val thumbnail: String,
    val quantity: Int,
    val price: Double,
    val id: Int,
    val title: String
)

@Serializable
data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)