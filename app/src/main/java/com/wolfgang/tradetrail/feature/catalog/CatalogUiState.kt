package com.wolfgang.tradetrail.feature.catalog

data class CatalogUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
