package com.example.praktikumppb.model

import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    val data: List<Character>
)

data class CharacterDetailResponse(
    val data: Character
)

data class Character(
    val mal_id: Int,
    val name: String,
    val images: Images,
    val nicknames: List<String>,
    @SerializedName("name_kanji")
    val nameKanji: String?,
    val about: String?
)