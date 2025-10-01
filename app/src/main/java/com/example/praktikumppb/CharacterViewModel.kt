package com.example.praktikumppb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Character
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.praktikumppb.model.SortOrder

class CharacterViewModel : ViewModel() {
    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun fetchTopCharacters() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getTopCharacters()
                _characterList.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun searchCharacters(query: String) {
        if (query.isBlank()) {
            fetchTopCharacters()
            return
        }
        viewModelScope.launch {
            try {
                _characterList.value = ApiClient.service.searchCharacters(query).data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // Fungsi untuk sorting karakter
    fun sortCharacterList(order: SortOrder) {
        val sortedList = when (order) {
            SortOrder.ASC -> _characterList.value.sortedBy { it.name }
            SortOrder.DESC -> _characterList.value.sortedByDescending { it.name }
        }
        _characterList.value = sortedList
    }
}
