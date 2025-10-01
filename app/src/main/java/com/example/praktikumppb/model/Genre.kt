package com.example.praktikumppb.model

data class GenreResponse(
    val data: List<Genre>
)

data class Genre(
    val mal_id: Int,
    val name: String
)