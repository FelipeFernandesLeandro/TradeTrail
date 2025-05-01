package com.wolfgang.tradetrail.core.data.api

import com.wolfgang.tradetrail.core.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TradeApi {
    @GET("products")
    suspend fun fetchProducts(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0,
        @Query("select") select: String? = null
    ): ProductResponse
}
