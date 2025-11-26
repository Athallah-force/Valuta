package com.athalah.valuta

<<<<<<< HEAD
=======
import androidx.compose.runtime.mutableStateOf
>>>>>>> origin/master
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.athalah.valuta.data.Currency
import com.athalah.valuta.data.getFlagForCurrency
import com.athalah.valuta.data.remote.ApiClient
<<<<<<< HEAD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
=======
>>>>>>> origin/master
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

<<<<<<< HEAD
    private val _state = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Loading)
    val state = _state.asStateFlow()

    private fun buyPrice(rate: Double): Double = rate * 0.98
    private fun sellPrice(rate: Double): Double = rate * 1.02

    fun loadRates() {
        viewModelScope.launch {

            try {
                _state.value = CurrencyUiState.Loading
=======
    var state = mutableStateOf<CurrencyUiState>(CurrencyUiState.Loading)
        private set

    private fun buyPrice(rate: Float): Float = rate * 0.98f
    private fun sellPrice(rate: Float): Float = rate * 1.02f

    fun loadRates() {
        viewModelScope.launch {
            try {
                state.value = CurrencyUiState.Loading
>>>>>>> origin/master

                val response = ApiClient.api.getLatestRates("USD")

                if (!response.isSuccessful) {
<<<<<<< HEAD
                    _state.value = CurrencyUiState.Error("API error: ${response.code()}")
=======
                    state.value = CurrencyUiState.Error("API error: ${response.code()}")
>>>>>>> origin/master
                    return@launch
                }

                val body = response.body()
                if (body == null) {
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/master
                        flag = getFlagForCurrency(body.base)
                    )
                )

<<<<<<< HEAD
                // Tambahkan currency lain
                body.rates.forEach { (code, value) ->
                    list.add(
                        Currency(
                            name = code,
                            buy = buyPrice(value),
                            sell = sellPrice(value),
=======
                // Semua currency dari API
                rates.forEach { (code, rate) ->
                    list.add(
                        Currency(
                            name = code,
                            buy = buyPrice(rate),
                            sell = sellPrice(rate),
>>>>>>> origin/master
                            flag = getFlagForCurrency(code)
                        )
                    )
                }

<<<<<<< HEAD
                _state.value = CurrencyUiState.Success(list)

            } catch (e: Exception) {
                _state.value = CurrencyUiState.Error(e.message ?: "Unknown error")
=======
                state.value = CurrencyUiState.Success(list)

            } catch (e: Exception) {
                state.value = CurrencyUiState.Error(e.message ?: "Unknown error")
>>>>>>> origin/master
            }
        }
    }
}
<<<<<<< HEAD
=======


>>>>>>> origin/master
