package com.wolfgang.tradetrail.core.data.api

import com.wolfgang.tradetrail.core.data.model.Product
import retrofit2.http.GET

interface TradeApi {
    @GET("products")
    suspend fun fetchProducts(): List<Product>
}
