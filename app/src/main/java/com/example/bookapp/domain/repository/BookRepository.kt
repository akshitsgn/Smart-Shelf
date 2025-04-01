package com.example.bookapp.domain.repository

import com.example.bookapp.domain.model.Book
import com.example.bookapp.utils.ApiResponse

interface BookRepository {
    suspend fun getRecommendations(userBooks: List<Book>): ApiResponse<List<Book>>
}