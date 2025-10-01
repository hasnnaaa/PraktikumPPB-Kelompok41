package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(navController: NavController, viewModel: CharacterDetailViewModel = viewModel()) {
    val characterDetail by viewModel.characterDetail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(characterDetail?.name ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (characterDetail == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val detail = characterDetail!!
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberAsyncImagePainter(detail.images.jpg.image_url),
                    contentDescription = detail.name,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(detail.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                if (detail.nameKanji != null) {
                    Text(detail.nameKanji, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("About", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(detail.about ?: "No information available.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}