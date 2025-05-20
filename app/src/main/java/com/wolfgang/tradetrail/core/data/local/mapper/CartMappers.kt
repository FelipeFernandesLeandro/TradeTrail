package com.wolfgang.tradetrail.core.data.local.mapper

import com.wolfgang.tradetrail.core.data.local.entity.CartItemEntity
import com.wolfgang.tradetrail.core.data.model.Cart
import com.wolfgang.tradetrail.core.data.model.CartBody
import com.wolfgang.tradetrail.core.data.model.Dimensions
import com.wolfgang.tradetrail.core.data.model.Meta
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.model.ProductReference

fun List<CartItemEntity>.toDomain(): Cart {
    val totalValue = sumOf { it.price * it.quantity }
    return Cart(
        id = 0,
        userId = 0,
        products = map { it.toDomainProduct() },
        totalQuantity = sumOf { it.quantity },
        totalProducts = size,
        total = totalValue,
        discountedTotal = totalValue
    )
}

private fun CartItemEntity.toDomainProduct(): Product =
    Product(
        id = productId,
        title = title,
        price = price,
        thumbnail = thumbnail,
        discountPercentage = 0.0,
        discountedTotal = price * quantity,
        total = price * quantity,
        quantity = quantity,
        description = "",
        category = "",
        stock = 0,
        tags = emptyList(),
        brand = "",
        sku = "",
        weight = 0.0,
        dimensions = Dimensions(0.0, 0.0, 0.0),
        warrantyInformation = "",
        shippingInformation = "",
        availabilityStatus = "",
        reviews = emptyList(),
        returnPolicy = "",
        minimumOrderQuantity = 1,
        meta = Meta("", "", "", ""),
        images = emptyList()
    )

fun Cart.toRemote(userId: Int): CartBody =
    CartBody(
        userId = userId,
        products = this.products.map {
            ProductReference(it.id, it.quantity)
        },
        merge = true
    )