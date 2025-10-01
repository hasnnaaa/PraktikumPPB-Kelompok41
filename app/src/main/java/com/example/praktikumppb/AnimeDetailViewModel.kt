package com.example.praktikumppb

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Anime
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _animeDetail = MutableStateFlow<Anime?>(null)
    val animeDetail: StateFlow<Anime?> = _animeDetail

    private val animeId: Int = checkNotNull(savedStateHandle["animeId"])

    init {
        fetchAnimeDetail()
    }

    private fun fetchAnimeDetail() {
        viewModelScope.launch {
            try {
                // Memanggil fungsi getAnimeDetail dari ApiService
                val response = ApiClient.service.getAnimeDetail(animeId)
                _animeDetail.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}