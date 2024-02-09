package com.simplesoftware.pokedexdabia.network.models

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val nextUrl: String?,
    @SerializedName("previous")
    val previousUrl: String?,
    @SerializedName("results")
    val pokemonList: List<PokemonResponse>?,
    @SerializedName("pokemon")
    val pokemonByTypeList: List<PokemonByTypeListResponse>?
)

data class PokemonByTypeListResponse(
    @SerializedName("pokemon")
    val pokemonByType: PokemonResponse?
)

data class PokemonResponse(
    @SerializedName("url")
    val url: String?
)

data class PokemonDetailsResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sprites")
    val sprite: PokemonSpriteResponse?,
    @SerializedName("types")
    val types: List<PokemonTypesResponse>?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("stats")
    val stats: List<PokemonStatsListResponse>?
)

data class PokemonSpriteResponse(
    @SerializedName("front_default")
    val imageUrl: String?
)

data class PokemonTypesResponse(
    @SerializedName("type")
    val type: PokemonTypeResponse?
)

data class PokemonTypeResponse(
    @SerializedName("name")
    val typeName: String?,
)

data class PokemonStatsListResponse(
    @SerializedName("base_stat")
    val baseValue: Int?,
    @SerializedName("stat")
    val stat: PokemonStatResponse?
)

data class PokemonStatResponse(
    @SerializedName("name")
    val statName: String?
)