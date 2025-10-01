package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktikumppb.model.SortOrder
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(navController: NavController, viewModel: AnimeViewModel = viewModel()) {
    val animeList by viewModel.animeList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val focusManager = LocalFocusManager.current
    val genres by viewModel.genres.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTopAnime()
    }

    // Gunakan Column untuk menampung Search, Sort, dan List
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            label = { Text("Search Anime...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.searchAnime(searchQuery)
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tombol Sorting
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.sortAnimeList(SortOrder.ASC) },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("A-Z")
            }
            Button(
                onClick = { viewModel.sortAnimeList(SortOrder.DESC) },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("Z-A")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tombol untuk kembali ke Top Anime
            item {
                FilterChip(
                    selected = false,
                    onClick = { viewModel.fetchTopAnime() },
                    label = { Text("Top Anime") }
                )
            }
            // Menampilkan semua genre dari API
            items(genres) { genre ->
                FilterChip(
                    selected = false, // Logika 'selected' bisa dikembangkan lebih lanjut jika perlu
                    onClick = { viewModel.filterAnimeByGenre(genre.mal_id) },
                    label = { Text(genre.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(animeList) { anime ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(Screen.AnimeDetail.createRoute(anime.mal_id))
                        }
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
                            contentDescription = anime.title,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(anime.title)
                            Text("Type: ${anime.type ?: "-"}")
                            Text("Episodes: ${anime.episodes ?: 0}")
                            Text("Score: ${anime.score ?: "N/A"}")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeListScreenPreviw(){
    AboutScreen()
}