package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.utils.ApiResponse


class GetRecommendedBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(userBooks: List<Book>): ApiResponse<List<Book>> {
        return repository.getRecommendations(userBooks)
    }
}
