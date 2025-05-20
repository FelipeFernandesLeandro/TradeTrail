package com.wolfgang.tradetrail.core.data.repository

import com.wolfgang.tradetrail.core.data.local.dao.CartDao
import com.wolfgang.tradetrail.core.data.local.entity.CartItemEntity
import com.wolfgang.tradetrail.core.data.local.mapper.toDomain
import com.wolfgang.tradetrail.core.data.local.mapper.toRemote
import com.wolfgang.tradetrail.core.data.model.Cart
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.remote.CartApi
import com.wolfgang.tradetrail.core.data.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

interface CartRepository {
    val current: StateFlow<Cart?>
    suspend fun add(product: Product, quantity: Int = 1)
    suspend fun changeQuantity(productId: Int, quantity: Int)
    suspend fun remove(productId: Int)
    suspend fun clear()

    suspend fun checkout(userId: Int): Result<Cart>
}

class CartRepositoryImpl @Inject constructor(
    private val api: CartApi,
    private val session: SessionManager,
    private val dao: CartDao
): CartRepository {

    override val current: StateFlow<Cart?> =
        dao.observeAll()
            .map { entities ->
                entities.takeIf { it.isNotEmpty() }?.toDomain()
            }
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    override suspend fun add(product: Product, quantity: Int) {
        val item = CartItemEntity(
            productId = product.id,
            title = product.title,
            price = product.price,
            thumbnail = product.thumbnail,
            quantity = quantity + (dao.getQuantity(product.id) ?: 0)
        )
        dao.upsert(item)
    }

    override suspend fun changeQuantity(productId: Int, quantity: Int) {
        if (quantity > 0) dao.updateQuantity(productId, quantity)
        else dao.delete(productId)
    }

    override suspend fun remove(productId: Int) {
        return dao.delete(productId)
    }

    override suspend fun clear() {
        dao.clear()
    }

    override suspend fun checkout(userId: Int): Result<Cart> = runCatching {
        checkNotNull(current.value)
        val payload = current.value!!.toRemote(userId)
        api.addToCart(payload)
            .also { clear() }
    }
}