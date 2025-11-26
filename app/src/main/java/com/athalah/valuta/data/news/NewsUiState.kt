package com.athalah.valuta.data.news

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val data: List<NewsArticle>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}
