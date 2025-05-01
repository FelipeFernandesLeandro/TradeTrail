package com.wolfgang.tradetrail.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wolfgang.tradetrail.core.data.api.TradeApi
import com.wolfgang.tradetrail.core.data.model.Product
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val api: TradeApi
): PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            val page = params.key ?: 0
            val limit = params.loadSize
            val skip = page * limit
            val response = api.fetchProducts(limit, skip)
            val products = response.products

            val nextKey = if (skip + limit < response.total) page + 1 else null
            val prevKey = if (page > 0) page - 1 else null

            return LoadResult.Page(
                products,
                prevKey,
                nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let {
            position -> state.closestPageToPosition(position)?.prevKey?.plus(1)
        }
    }
}