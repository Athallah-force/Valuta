package com.athalah.valuta.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Base URL: https://api.frankfurter.app/latest

data class CurencyApi(
    val base: String,
    val rates: Map<String, Float>
)

interface CurrencyApi {

    @GET("latest")
    suspend fun getLatestRates(
        @Query("from") base: String = "USD"
    ): Response<ExchangeRatesResponse>
}
