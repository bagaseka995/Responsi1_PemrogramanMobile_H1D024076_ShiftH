package com.example.bukuapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bukuapp.data.model.authorText
import com.example.bukuapp.data.model.yearText
import com.example.bukuapp.ui.components.DetailRow
import com.example.bukuapp.ui.viewmodel.BookViewModel

// Layar Detail Buku menampilkan informasi lengkap buku tanpa panggilan API kedua
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    bookKey: String,
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Mengambil data buku langsung dari state ViewModel saat ini
    val book = viewModel.getBookByKey(bookKey)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Buku",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text(
                            text = "←",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        if (book == null) {
            // Tampilan jika data buku tidak ditemukan
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Buku tidak ditemukan",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kunci: $bookKey",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBackClick) {
                        Text("Kembali ke Katalog")
                    }
                }
            }
        } else {
            // Tampilan rincian informasi buku
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        // Judul Buku
                        Text(
                            text = book.title ?: "Tanpa Judul",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Penulis
                        DetailRow(
                            label = "Penulis",
                            value = book.authorText()
                        )

                        // Tahun Terbit Pertama
                        DetailRow(
                            label = "Tahun Terbit Pertama",
                            value = book.yearText()
                        )

                        // Jumlah Edisi
                        val edisiText = book.editionCount?.let { "$it edisi terdaftar" } ?: "Tidak ada data edisi"
                        DetailRow(
                            label = "Jumlah Edisi",
                            value = edisiText
                        )

                        // Bahasa
                        val bahasaText = if (!book.language.isNullOrEmpty()) {
                            book.language.joinToString(", ").uppercase()
                        } else {
                            "Informasi bahasa tidak tersedia"
                        }
                        DetailRow(
                            label = "Bahasa",
                            value = bahasaText
                        )

                        // Kunci Karya (Work Key)
                        DetailRow(
                            label = "Kunci Buku (Open Library ID)",
                            value = book.key ?: "-"
                        )
                    }
                }
            }
        }
    }
}
