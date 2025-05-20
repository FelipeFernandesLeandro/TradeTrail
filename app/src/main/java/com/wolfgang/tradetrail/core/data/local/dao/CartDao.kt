package com.wolfgang.tradetrail.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wolfgang.tradetrail.core.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun observeAll(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: CartItemEntity)

    @Query("UPDATE cart_items SET quantity = :qty WHERE productId = :id")
    suspend fun updateQuantity(id: Int, qty: Int)

    @Query("DELETE FROM cart_items WHERE productId = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clear()

    @Query("SELECT quantity FROM cart_items WHERE productId = :id")
    suspend fun getQuantity(id: Int): Int?
}