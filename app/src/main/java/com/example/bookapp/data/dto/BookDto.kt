package com.example.bookapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val title: String,
    val genre: String,
    val imageUrl: String
)