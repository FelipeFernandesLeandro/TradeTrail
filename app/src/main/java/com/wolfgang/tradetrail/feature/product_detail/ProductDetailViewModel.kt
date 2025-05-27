package com.wolfgang.tradetrail.feature.product_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.repository.CartRepository
import com.wolfgang.tradetrail.core.data.repository.ProductRepository
import com.wolfgang.tradetrail.navigation.NavKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val productId: Int = checkNotNull(savedStateHandle[NavKeys.PRODUCT_ID])
    var uiState by mutableStateOf(ProductDetailUiState())
        private set

    val cartCount: StateFlow<Int> = cartRepo.current
        .map { it?.products?.sumOf { product -> product.quantity } ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    init {
        fetchProduct(productId)
    }

    fun fetchProduct(id: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val product = productRepo.fetchProduct(id.toString())
                val reviewCount = product.reviews.size
                val averageRating = if (reviewCount > 0) {
                    product.reviews.sumOf { it.rating }.toDouble() / reviewCount
                } else {
                    0.0
                }
                uiState = uiState.copy(
                    product = product,
                    isLoading = false,
                    success = true,
                    error = null,
                    averageRating = averageRating,
                    reviewCount = reviewCount
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, success = false, error = e.message)
            }
        }
    }

    fun addToCart(product: Product) = viewModelScope.launch {
        cartRepo.add(product, 1)
    }
}