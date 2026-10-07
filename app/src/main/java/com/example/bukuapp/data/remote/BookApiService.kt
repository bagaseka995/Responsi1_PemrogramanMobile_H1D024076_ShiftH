package com.example.bukuapp.data.remote

import com.example.bukuapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interface Retrofit untuk endpoint Open Library
interface BookApiService {

    // Endpoint pencarian buku dengan query q dan batas jumlah hasil
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20
    ): SearchResponse
}
