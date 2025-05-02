package com.wolfgang.tradetrail.core.data.repository

import androidx.paging.PagingData
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.model.ProductResponse
import kotlinx.coroutines.flow.Flow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wolfgang.tradetrail.core.data.remote.ProductApi
import com.wolfgang.tradetrail.core.data.paging.ProductPagingSource
import javax.inject.Inject


interface ProductRepository {
    suspend fun all(): ProductResponse

    fun allPaged(): Flow<PagingData<Product>>

    suspend fun fetchProduct(id: String): Product
}

class ProductRepositoryImpl @Inject constructor(private val api: ProductApi) : ProductRepository {
    override suspend fun all(): ProductResponse = api.fetchProducts()

    override fun allPaged(): Flow<PagingData<Product>> = Pager(PagingConfig(20)) {
        ProductPagingSource(api)
    }.flow

    override suspend fun fetchProduct(id: String): Product = api.fetchProduct(id)
}
