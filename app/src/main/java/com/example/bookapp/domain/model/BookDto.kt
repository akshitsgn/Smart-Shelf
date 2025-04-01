package com.example.bookapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val title: String,
    val genre: String,
    val imageUrl: String
)