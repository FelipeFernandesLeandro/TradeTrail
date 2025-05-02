package com.wolfgang.tradetrail.core.data.remote

import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun fetchProducts(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0,
        @Query("select") select: String? = null
    ): ProductResponse

    @GET("products/{id}")
    suspend fun fetchProduct(@Path("id") id: String): Product
}