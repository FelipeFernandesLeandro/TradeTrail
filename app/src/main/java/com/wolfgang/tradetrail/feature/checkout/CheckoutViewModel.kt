package com.wolfgang.tradetrail.feature.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfgang.tradetrail.core.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repo: CartRepository
) : ViewModel() {
    private val _checkoutSuccess = MutableStateFlow(false)

    val uiState: StateFlow<CheckoutUiState> =
        combine(repo.current, _checkoutSuccess) { cart, isSuccess ->
            val baseState = when (cart) {
                null -> CheckoutUiState(emptyList(), 0.0, 0.0, 0, loading = false)
                else -> CheckoutUiState(
                    items = cart.products.map { it.toUi() },
                    total = cart.total,
                    discountedTotal = cart.discountedTotal,
                    totalQuantity = cart.totalQuantity,
                    loading = false
                )
            }
            baseState.copy(success = isSuccess)
        }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                CheckoutUiState(loading = true, success = _checkoutSuccess.value)
            )

    fun remove(id: Int) = viewModelScope.launch { repo.remove(id) }

    fun checkout() = viewModelScope.launch {
        repo.clear()
        _checkoutSuccess.value = true
        delay(2_000)
        _checkoutSuccess.value = false
    }

    fun resetCheckoutSuccess() {
        _checkoutSuccess.value = false
    }
}
