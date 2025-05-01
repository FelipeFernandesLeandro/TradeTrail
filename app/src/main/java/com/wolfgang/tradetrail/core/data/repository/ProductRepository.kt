package com.wolfgang.tradetrail.core.data.repository

import androidx.paging.PagingData
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.model.ProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun all(): ProductResponse

    fun allPaged(): Flow<PagingData<Product>>
}
