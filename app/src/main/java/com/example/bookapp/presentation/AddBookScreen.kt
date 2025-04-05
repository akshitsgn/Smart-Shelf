package com.example.bookapp.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookapp.domain.model.Book
import kotlinx.coroutines.launch


@Composable
fun AddBookScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: BookViewModel = hiltViewModel()
    var title by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add a New Book", style = MaterialTheme.typography.headlineSmall)

        // Title Input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Genre Input
        OutlinedTextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Genre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Image URL Input
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Book Button
        Button(
            onClick = {
                if (title.isNotEmpty() && genre.isNotEmpty()) {
                    viewModel.addBook(Book(title, genre, imageUrl))

                    // Show Snackbar
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Book added successfully!")
                    }

                    // Clear inputs
                    title = ""
                    genre = ""
                    imageUrl = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Book")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate Back Button
        Button(
            onClick = { onNavigateBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Book List")
        }

        // Snackbar Host
        SnackbarHost(hostState = snackbarHostState)
    }

}
