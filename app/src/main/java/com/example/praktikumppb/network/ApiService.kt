package com.example.praktikumppb.network

import com.example.praktikumppb.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): AnimeResponse

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): AnimeListResponse

    @GET("characters/{id}/full")
    suspend fun getCharacterDetail(@Path("id") id: Int): CharacterDetailResponse

    @GET("top/characters")
    suspend fun getTopCharacters(): CharacterListResponse

    @GET("characters")
    suspend fun searchCharacters(@Query("q") query: String): CharacterListResponse

    @GET("genres/anime")
    suspend fun getAnimeGenres(): GenreResponse

    @GET("anime")
    suspend fun filterAnimeByGenre(@Query("genres") genreId: Int): AnimeListResponse
}
