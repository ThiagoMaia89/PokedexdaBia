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
    val pokemonList: List<PokemonResponse>?
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
    val sprite: PokemonSpriteResponse?
)

data class PokemonSpriteResponse(
    @SerializedName("front_default")
    val imageUrl: String?
)