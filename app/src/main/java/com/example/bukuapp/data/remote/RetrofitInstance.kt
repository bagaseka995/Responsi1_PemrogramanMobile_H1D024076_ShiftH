package com.example.bukuapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object untuk konfigurasi dan inisialisasi Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://openlibrary.org/"

    // Inisialisasi BookApiService secara lazy
    val api: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }
}
