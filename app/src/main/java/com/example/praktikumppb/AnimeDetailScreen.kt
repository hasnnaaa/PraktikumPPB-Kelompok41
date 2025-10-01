package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(navController: NavController, viewModel: AnimeDetailViewModel = viewModel()) {
    // Mengambil data detail anime dari ViewModel
    val animeDetail by viewModel.animeDetail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(animeDetail?.title ?: "Loading...") },
                navigationIcon = {
                    // Tombol untuk kembali ke halaman sebelumnya
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Tampilkan loading indicator jika data masih kosong (sedang diambil dari API)
        if (animeDetail == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Jika data sudah ada, tampilkan konten detailnya
            val detail = animeDetail!!
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    // Membuat Column bisa di-scroll jika kontennya panjang
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberAsyncImagePainter(detail.images.jpg.image_url),
                    contentDescription = detail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(detail.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Score: ${detail.score ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                Text("Episodes: ${detail.episodes ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Synopsis", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(detail.synopsis ?: "No synopsis available.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}