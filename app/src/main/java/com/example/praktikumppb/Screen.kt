package com.example.praktikumppb

sealed class Screen(val route: String, val title: String) {
    object Anime : Screen("anime", "Anime")
    object About : Screen("about", "About")
    object AnimeDetail : Screen("anime/{animeId}", "Anime Detail")
    object Character : Screen("character", "Character")
    object CharacterDetail : Screen("character/{characterId}", "Character Detail")
    fun createRoute(id: Int): String {
        return when (this) {
            is AnimeDetail -> "anime/$id"
            is CharacterDetail -> "character/$id"
            else -> route
        }
    }
}

