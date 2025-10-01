package com.example.praktikumppb

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Character
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _characterDetail = MutableStateFlow<Character?>(null)
    val characterDetail: StateFlow<Character?> = _characterDetail

    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    init {
        fetchCharacterDetail()
    }

    private fun fetchCharacterDetail() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getCharacterDetail(characterId)
                _characterDetail.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}