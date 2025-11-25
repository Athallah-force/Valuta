package com.athalah.valuta.data.repository

import com.athalah.valuta.data.remote.ApiClient
import com.athalah.valuta.data.remote.ExchangeRatesResponse
import retrofit2.Response

class CurrencyRepository {

    suspend fun getRates(base: String = "USD"): Response<ExchangeRatesResponse> {
        return ApiClient.api.getLatestRates(base)

    }
}
