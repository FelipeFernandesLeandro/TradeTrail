package com.wolfgang.tradetrail.feature.catalog

import com.wolfgang.tradetrail.core.data.model.Product

data class ProductDetailUiState (
    val product: Product? = null,
    val averageRating: Double = 0.0,
    val reviewCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)