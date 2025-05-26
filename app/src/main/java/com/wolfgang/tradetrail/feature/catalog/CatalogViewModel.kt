package com.wolfgang.tradetrail.feature.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.data.repository.CartRepository
import com.wolfgang.tradetrail.core.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    repo: ProductRepository,
    private val cartRepo: CartRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CatalogUiState())
        private set

    val products = repo.allPaged().cachedIn(viewModelScope).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty()
    )

    val cartCount: StateFlow<Int> = cartRepo.current
        .map { it?.products?.sumOf { product -> product.quantity } ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun addToCart(product: Product) = viewModelScope.launch {
        cartRepo.add(product, 1)
    }

    fun errorShown() {
        uiState = uiState.copy(error = null)
    }
}