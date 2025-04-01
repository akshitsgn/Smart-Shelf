package com.example.bookapp.data.repository



import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.utils.ApiResponse
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.serialization.json.Json

class BookRepositoryImpl(private val model: GenerativeModel) : BookRepository {

    override suspend fun getRecommendations(userBooks: List<Book>): ApiResponse<List<Book>> {
        val prompt = """
            I have read these books. Return the result in JSON format only.
            The response must be a JSON array containing objects with fields: "title", "genre", "imageUrl".
            Example output: [{"title": "Book1", "genre": "Fiction", "imageUrl": "url1"}].
            No extra text, just JSON. dont add same book let the user first add book Recommend similar books from Libris Cover Image API, Open Library Covers API, Bookcover-API by w3slley, ISBNdb API book any 5 with valid image urls and also check whethet the image url is of book or not before giving use real image url: ${userBooks.joinToString { it.title }}.
        """.trimIndent()

        val response = model.generateContent(prompt)

        println("Raw Response: ${response.text}") // Log the raw response for debugging

        response.text?.let { jsonResponse ->
            val jsonArrayRegex = """\[[\s\S]*]""".toRegex() // Matches any JSON array
            val matchResult = jsonArrayRegex.find(jsonResponse)

            return matchResult?.value?.let {
                try {
                    val books = Json.decodeFromString<List<Book>>(it)
                    ApiResponse(success = true, message = "Books fetched successfully", data = books)
                } catch (e: Exception) {
                    println("JSON Parsing Error: ${e.message}") // Log the error
                    ApiResponse(success = false, message = "Invalid JSON format", data = emptyList())
                }
            } ?: ApiResponse(success = false, message = "No valid JSON response", data = emptyList())
        } ?: return ApiResponse(success = false, message = "Empty response from model", data = emptyList())
    }
}
