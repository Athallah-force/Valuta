package com.athalah.valuta.data.news

class NewsRepository {

    private val api = NewsRetrofitInstance.api
    private val apiKey = "pub_719aee7505b54e0381f60158693a39b2"

    suspend fun getNews(query: String = ""): List<NewsArticle> {

        val response = api.getNews(
            apiKey = apiKey,
            query = query,
            category = "business"
        )

        if (!response.isSuccessful) {
            throw Exception("HTTP ${response.code()}")
        }

        val items = response.body()?.results ?: emptyList()

        return items.map {
            NewsArticle(
                title = it.title ?: "",
                description = it.description ?: "",
                url = it.link ?: "",
                image = it.image_url,
                publishedAt = it.pubDate ?: "",
                source = "newsdata.io"
            )
        }
    }
}
