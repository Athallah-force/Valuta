package com.athalah.valuta.data.news

data class NewsResponse(
    val status: String,
    val totalResults: Int?,
    val results: List<NewsItem>?
)

data class NewsItem(
    val title: String?,
    val description: String?,
    val link: String?,
    val image_url: String?,
    val pubDate: String?
)
