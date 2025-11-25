package com.athalah.valuta

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.athalah.valuta.data.Currency
import com.athalah.valuta.data.getFlagForCurrency
import com.athalah.valuta.data.remote.ApiClient
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    var state = mutableStateOf<CurrencyUiState>(CurrencyUiState.Loading)
        private set

    private fun buyPrice(rate: Float): Float = rate * 0.98f
    private fun sellPrice(rate: Float): Float = rate * 1.02f

    fun loadRates() {
        viewModelScope.launch {
            try {
                state.value = CurrencyUiState.Loading

                val response = ApiClient.api.getLatestRates("USD")

                if (!response.isSuccessful) {
                    state.value = CurrencyUiState.Error("API error: ${response.code()}")
                    return@launch
                }

                val body = response.body()
                if (body == null) {
                    state.value = CurrencyUiState.Error("Empty API response")
                    return@launch
                }

                // Convert Double → Float
                val rates: Map<String, Float> = body.rates.mapValues { it.value.toFloat() }

                val list = mutableListOf<Currency>()

                // Base currency (USD)
                list.add(
                    Currency(
                        name = body.base,
                        buy = buyPrice(1f),
                        sell = sellPrice(1f),
                        flag = getFlagForCurrency(body.base)
                    )
                )

                // Semua currency dari API
                rates.forEach { (code, rate) ->
                    list.add(
                        Currency(
                            name = code,
                            buy = buyPrice(rate),
                            sell = sellPrice(rate),
                            flag = getFlagForCurrency(code)
                        )
                    )
                }

                state.value = CurrencyUiState.Success(list)

            } catch (e: Exception) {
                state.value = CurrencyUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}


