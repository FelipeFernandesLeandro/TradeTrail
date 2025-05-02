package com.wolfgang.tradetrail.feature.catalog

import com.wolfgang.tradetrail.core.data.model.Product

data class ProductDetailUiState (
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)