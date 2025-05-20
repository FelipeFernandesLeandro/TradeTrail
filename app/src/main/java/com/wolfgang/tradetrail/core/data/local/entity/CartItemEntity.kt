package com.wolfgang.tradetrail.core.data.local.entity

import androidx.room.Entity

@Entity(tableName = "cart_items", primaryKeys = ["productId"])
data class CartItemEntity(
    val productId: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val quantity: Int,
)