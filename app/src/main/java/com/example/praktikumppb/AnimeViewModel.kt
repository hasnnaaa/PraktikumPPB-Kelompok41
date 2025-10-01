package com.example.praktikumppb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Anime
import com.example.praktikumppb.model.AnimeListResponse
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.praktikumppb.model.SortOrder
import com.example.praktikumppb.model.Genre

class AnimeViewModel : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    init {
        // Panggil fungsi ini saat ViewModel pertama kali dibuat
        fetchGenres()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            try {
                _genres.value = ApiClient.service.getAnimeGenres().data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun filterAnimeByGenre(genreId: Int) {
        viewModelScope.launch {
            try {
                _animeList.value = ApiClient.service.filterAnimeByGenre(genreId).data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun fetchTopAnime() {
        viewModelScope.launch {
            try {
                val response: AnimeListResponse = ApiClient.service.getTopAnime()
                _animeList.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk melakukan pencarian anime
    fun searchAnime(query: String) {
        if (query.isBlank()) {
            fetchTopAnime() // Kembali ke daftar teratas jika query kosong
            return
        }
        viewModelScope.launch {
            try {
                _animeList.value = ApiClient.service.searchAnime(query).data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun sortAnimeList(order: SortOrder) {
        val sortedList = when (order) {
            SortOrder.ASC -> _animeList.value.sortedBy { it.title }
            SortOrder.DESC -> _animeList.value.sortedByDescending { it.title }
        }
        _animeList.value = sortedList
    }
}
