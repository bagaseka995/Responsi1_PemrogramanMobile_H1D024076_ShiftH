package com.example.bukuapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuapp.data.model.BookDoc
import com.example.bukuapp.data.repository.BookRepository
import com.example.bukuapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel untuk mengelola state katalog buku dan logika pencarian
class BookViewModel(
    private val repository: BookRepository = BookRepository()
) : ViewModel() {

    // State internal untuk UI State
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // State internal untuk teks pencarian dengan default "indonesia"
    private val _query = MutableStateFlow("indonesia")
    val query: StateFlow<String> = _query.asStateFlow()

    // Inisialisasi awal: otomatis mencari buku dengan kata kunci default
    init {
        searchBooks()
    }

    // Mengubah query pencarian saat user mengetik di search bar
    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    // Melakukan pencarian buku menggunakan viewModelScope
    fun searchBooks() {
        val currentQuery = _query.value.trim()
        if (currentQuery.isEmpty()) {
            _uiState.value = UiState.Error("Kata kunci pencarian tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val results = repository.searchBooks(query = currentQuery)
                if (results.isEmpty()) {
                    _uiState.value = UiState.Error("Tidak ada buku yang ditemukan untuk kata kunci: \"$currentQuery\"")
                } else {
                    _uiState.value = UiState.Success(books = results)
                }
            } catch (e: Exception) {
                // Tangani error koneksi / parsing dengan pesan yang informatif
                val message = e.localizedMessage ?: "Terjadi kesalahan pada jaringan atau server"
                _uiState.value = UiState.Error("Gagal memuat buku: $message")
            }
        }
    }

    // Mengambil data buku spesifik dari daftar Success tanpa perlu API call kedua
    fun getBookByKey(key: String): BookDoc? {
        val currentState = _uiState.value
        return if (currentState is UiState.Success) {
            currentState.books.find { it.key == key }
        } else {
            null
        }
    }
}
