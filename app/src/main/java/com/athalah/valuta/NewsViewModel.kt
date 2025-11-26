package com.athalah.valuta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.athalah.valuta.data.news.NewsRepository
import com.athalah.valuta.data.news.NewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _state = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val state: StateFlow<NewsUiState> = _state

    init {
        loadNews()
    }

    fun loadNews() {
        _state.value = NewsUiState.Loading

        viewModelScope.launch {
            try {
                val result = repository.getNews("finance")
                _state.value = NewsUiState.Success(result)
            } catch (e: Exception) {
                _state.value = NewsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
