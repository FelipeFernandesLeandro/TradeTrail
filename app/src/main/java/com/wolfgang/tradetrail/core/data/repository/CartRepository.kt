package com.wolfgang.tradetrail.core.data.repository

import com.wolfgang.tradetrail.core.data.model.Cart
import com.wolfgang.tradetrail.core.data.model.CartBody
import com.wolfgang.tradetrail.core.data.model.CartResponse
import com.wolfgang.tradetrail.core.data.model.ProductReference
import com.wolfgang.tradetrail.core.data.model.UpdateCartBody
import com.wolfgang.tradetrail.core.data.remote.CartApi
import com.wolfgang.tradetrail.core.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

interface CartRepository {
    val current: StateFlow<Cart?>
    suspend fun refresh(userId: Int): CartResponse?
    suspend fun add(productId: Int, quantity: Int = 1): Cart
    suspend fun changeQuantity(productId: Int, quantity: Int): Cart
    suspend fun remove(productId: Int): Cart
    suspend fun clear()
}

class CartRepositoryImpl @Inject constructor(private val api: CartApi, private val session: SessionManager): CartRepository {
    private val _current = MutableStateFlow<Cart?>(null)
    override val current: StateFlow<Cart?> = _current

    override suspend fun refresh(userId: Int): CartResponse? {
        val response = api.getCartByUser(userId)
        _current.value = response?.carts?.firstOrNull()
        return response
    }

    override suspend fun add(productId: Int, quantity: Int): Cart {
        val cart = _current.value
        val products = listOf(ProductReference(productId, quantity))
        val userId: Int = session.userIdFlow.firstOrNull() ?: 1

        if (cart != null) {
            val body = UpdateCartBody(userId, products, true)
            api.updateCart(cart.id, body)
        } else {
            val body = CartBody(userId, products)
            api.addToCart(body)
        }.also {
            _current.value = it
            return it
        }
    }

    override suspend fun changeQuantity(productId: Int, quantity: Int): Cart {
        val cartId = requireNotNull(_current.value?.id) { "Cart not initialized" }
        val userId: Int = session.userIdFlow.firstOrNull() ?: 1
        val body = UpdateCartBody(userId, listOf(ProductReference(productId, quantity)), false)
        val updatedCart = api.updateCart(cartId, body)

        _current.value = updatedCart
        return updatedCart
    }

    override suspend fun remove(productId: Int): Cart {
        return changeQuantity(productId, 0)
    }

    override suspend fun clear() {
        _current.value?.let { api.deleteCart(it.id) }
        _current.value = null
    }
}