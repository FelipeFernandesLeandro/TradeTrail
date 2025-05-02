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
    val title: String,
    val description: String,
    val category: String,
    val stock: Int,
    val tags: List<String>,
    val brand: String,
    val sku: String,
    val weight: Double,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>
)

@Serializable
data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

@Serializable
data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)

@Serializable
data class Meta(
    val createdAt: String,
    val updatedAt: String,
    val barcode: String,
    val qrCode: String
)

@Serializable
data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)