package com.wolfgang.tradetrail.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wolfgang.tradetrail.core.data.api.TradeApi
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.model.ProductResponse
import com.wolfgang.tradetrail.core.data.paging.ProductPagingSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ProductRepoImpl @Inject constructor(private val api: TradeApi) : ProductRepository {
    override suspend fun all(): ProductResponse = api.fetchProducts()

    override fun allPaged(): Flow<PagingData<Product>> = Pager(PagingConfig(20)) {
        ProductPagingSource(api)
    }.flow
}
