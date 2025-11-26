package com.athalah.valuta.data.news

data class NewsArticle(
    val title: String,
    val description: String,
    val url: String,
    val image: String?,
    val publishedAt: String,
    val source: String
)
