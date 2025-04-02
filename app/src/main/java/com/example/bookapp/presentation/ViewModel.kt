package com.example.bookapp.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.usecase.GetRecommendedBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookViewModel @Inject constructor(
    private val getRecommendedBooksUseCase: GetRecommendedBooksUseCase
) : ViewModel() {

    private val _userBooks = MutableStateFlow<List<Book>>(emptyList())
    val userBooks: StateFlow<List<Book>> = _userBooks

    private val _recommendedBooks = MutableStateFlow<List<Book>>(emptyList())
    val recommendedBooks: StateFlow<List<Book>> = _recommendedBooks

    fun addBook(book: Book) {
        _userBooks.value = _userBooks.value + book
    }

    fun getRecommendations() {
        viewModelScope.launch {
            val response =
                getRecommendedBooksUseCase(_userBooks.value) // Returns ApiResponse<List<Book>>

            if (response.success) {
                _recommendedBooks.value = response.data // Extract only the book list
            }
        }
    }
}

