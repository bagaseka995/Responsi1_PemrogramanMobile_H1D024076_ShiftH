package com.example.bukuapp.data.model

import com.google.gson.annotations.SerializedName

// Model pembungkus respons pencarian dari Open Library API
data class SearchResponse(
    @SerializedName("docs")
    val docs: List<BookDoc> = emptyList()
)

// Model data dokumen buku
data class BookDoc(
    @SerializedName("key")
    val key: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("author_name")
    val authorName: List<String>? = null,

    @SerializedName("first_publish_year")
    val firstPublishYear: Int? = null,

    @SerializedName("edition_count")
    val editionCount: Int? = null,

    @SerializedName("language")
    val language: List<String>? = null
)

// Extension function untuk format nama penulis dengan null safety
fun BookDoc.authorText(): String {
    return if (!authorName.isNullOrEmpty()) {
        authorName.joinToString(", ")
    } else {
        "Penulis tidak diketahui"
    }
}

// Extension function untuk format tahun terbit dengan null safety
fun BookDoc.yearText(): String {
    return firstPublishYear?.toString() ?: "Tahun tidak diketahui"
}
