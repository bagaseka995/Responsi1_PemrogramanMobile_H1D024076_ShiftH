package com.example.bukuapp.ui.state

import com.example.bukuapp.data.model.BookDoc

// Sealed interface untuk merepresentasikan state tampilan (Loading, Success, Error)
sealed interface UiState {
    data object Loading : UiState
    data class Success(val books: List<BookDoc>) : UiState
    data class Error(val message: String) : UiState
}
