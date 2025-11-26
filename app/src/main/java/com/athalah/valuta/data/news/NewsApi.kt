package com.athalah.valuta.data.news

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("api/1/news")
    suspend fun getNews(
        @Query("apikey") apiKey: String,
        @Query("q") query: String? = null,
        @Query("category") category: String? = null
    ): Response<NewsResponse>

}
