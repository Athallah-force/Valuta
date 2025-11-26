package com.athalah.valuta.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Example: https://api.frankfurter.app/latest?from=USD
interface CurrencyApi {

    @GET("latest")
    suspend fun getLatestRates(
        @Query("from") base: String = "USD"
    ): Response<ExchangeRatesResponse>
}
