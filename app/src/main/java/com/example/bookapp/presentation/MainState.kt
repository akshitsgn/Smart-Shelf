package com.example.bookapp.presentation

import com.example.bookapp.domain.model.Book

data class BookUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)