package com.wolfgang.tradetrail.core.data.repository

import com.wolfgang.tradetrail.core.data.api.TradeApi
import com.wolfgang.tradetrail.core.data.model.Product
import jakarta.inject.Inject

interface ProductRepository {
    suspend fun all(): List<Product>
}

class ProductRepoImpl @Inject constructor(private val api: TradeApi) : ProductRepository {
    override suspend fun all(): List<Product> = api.fetchProducts()
}