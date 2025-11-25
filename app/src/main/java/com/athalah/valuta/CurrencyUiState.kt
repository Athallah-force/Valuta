package com.athalah.valuta

import com.athalah.valuta.data.Currency

sealed class CurrencyUiState {

    object Loading : CurrencyUiState()

    data class Success(val data: List<Currency>) : CurrencyUiState()

    data class Error(val message: String) : CurrencyUiState()
}
