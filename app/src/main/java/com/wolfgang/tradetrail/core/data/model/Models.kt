package com.wolfgang.tradetrail.core.data.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String
)

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)