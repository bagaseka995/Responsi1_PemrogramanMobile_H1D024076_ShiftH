package com.example.bukuapp.data.repository

import com.example.bukuapp.data.model.BookDoc
import com.example.bukuapp.data.remote.BookApiService
import com.example.bukuapp.data.remote.RetrofitInstance

// Repository untuk abstraksi pemanggilan data buku dari API
class BookRepository(
    private val apiService: BookApiService = RetrofitInstance.api
) {
    // Memanggil API searchBooks dan mengembalikan list data dokumen buku
    suspend fun searchBooks(query: String, limit: Int = 20): List<BookDoc> {
        val response = apiService.searchBooks(query = query, limit = limit)
        return response.docs
    }
}
