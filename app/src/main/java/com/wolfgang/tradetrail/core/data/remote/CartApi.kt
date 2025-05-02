package com.wolfgang.tradetrail.core.data.remote

import com.wolfgang.tradetrail.core.data.model.Cart
import com.wolfgang.tradetrail.core.data.model.CartBody
import com.wolfgang.tradetrail.core.data.model.CartResponse
import com.wolfgang.tradetrail.core.data.model.UpdateCartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApi {
    @GET("cart/user/{userId}")
    suspend fun getCartByUser(
        @Path("userId") userId: Int
    ): CartResponse?

    @POST("carts/add")
    suspend fun addToCart(
        @Body body: CartBody
    ): Cart

    @PUT("carts/{cartId}")
    suspend fun updateCart(
        @Path("cartId") cartId: Int,
        @Body body: UpdateCartBody
    ): Cart

    @DELETE("carts/{cartId}")
    suspend fun deleteCart(
        @Path("cartId") cartId: Int
    ): Cart
}