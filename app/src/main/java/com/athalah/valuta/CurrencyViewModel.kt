package com.athalah.valuta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.athalah.valuta.data.Currency
import com.athalah.valuta.data.getFlagForCurrency
import com.athalah.valuta.data.remote.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val _state = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Loading)
    val state = _state.asStateFlow()

    private fun buyPrice(rate: Double): Double = rate * 0.98
    private fun sellPrice(rate: Double): Double = rate * 1.02

    fun loadRates() {
        viewModelScope.launch {

            try {
                _state.value = CurrencyUiState.Loading

                val response = ApiClient.api.getLatestRates("USD")

                if (!response.isSuccessful) {
                    _state.value = CurrencyUiState.Error("API error: ${response.code()}")
                    return@launch
                }

                val body = response.body()
                if (body == null) {
                    _state.value = CurrencyUiState.Error("Empty API response")
                    return@launch
                }

                val list = mutableListOf<Currency>()

                // Base currency (selalu 1)
                list.add(
                    Currency(
                        name = body.base,
                        buy = buyPrice(1.0),
                        sell = sellPrice(1.0),
                        flag = getFlagForCurrency(body.base)
                    )
                )

                // Tambahkan currency lain
                body.rates.forEach { (code, value) ->
                    list.add(
                        Currency(
                            name = code,
                            buy = buyPrice(value),
                            sell = sellPrice(value),
                            flag = getFlagForCurrency(code)
                        )
                    )
                }

                _state.value = CurrencyUiState.Success(list)

            } catch (e: Exception) {
                _state.value = CurrencyUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
