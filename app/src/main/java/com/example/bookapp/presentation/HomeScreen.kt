package com.example.bookapp.presentation

import com.example.bookapp.domain.model.Book
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage


@Composable
fun BookScreen(
    onNavigateToAddBook: () -> Unit
) {
    val viewModel: BookViewModel = hiltViewModel()
    val userBooks by viewModel.userBooks.collectAsState()
    val recommendedBooks by viewModel.recommendedBooks.collectAsState()

    // Ensuring UI recomposes when books change
    LaunchedEffect(userBooks) {
        if(userBooks.isNotEmpty()) {
            viewModel.getRecommendations()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Your Books", style = MaterialTheme.typography.headlineSmall)

        LazyColumn {
            items(userBooks) { book ->
                BookItem(book)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Book Button
        Button(onClick = { onNavigateToAddBook() }) {
            Text("Add a Book")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Get Recommendations Button (Fixed Visibility Issue)
        if (userBooks.isNotEmpty()) {
            Button(onClick = { viewModel.getRecommendations() }) {
                Text("Get Recommendations")
            }
        }

        // Recommended Books Section
        if (recommendedBooks.isNotEmpty()) {
            Text(text = "Recommended Books", style = MaterialTheme.typography.headlineSmall)

            LazyColumn {
                items(recommendedBooks) { book ->
                    BookItem(book)
                }
            }
        }
        AddBookScreen {  }
    }
}

@Composable
fun BookItem(book: Book) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        AsyncImage(
            model = book.imageUrl,  // Correct way to load image
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(book.title, style = MaterialTheme.typography.bodyLarge)
            Text(book.genre, style = MaterialTheme.typography.bodySmall)
        }
    }
}
