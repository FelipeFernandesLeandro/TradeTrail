package com.wolfgang.tradetrail.core.data.remote

import com.wolfgang.tradetrail.core.data.model.Cart
import com.wolfgang.tradetrail.core.data.model.CartBody
import retrofit2.http.Body
import retrofit2.http.POST

fun interface CartApi {
    @POST("carts/add")
    suspend fun addToCart(
        @Body body: CartBody
    ): Cart
}