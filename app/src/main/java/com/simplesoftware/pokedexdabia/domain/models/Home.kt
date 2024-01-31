package com.simplesoftware.pokedexdabia.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    val count: Int?,
    val nextUrl: String?,
    val previousUrl: String?,
    val pokemonList: List<Pokemon>
) : Parcelable

@Parcelize
data class Pokemon(
    val url: String?
) : Parcelable

@Parcelize
data class PokemonDetails(
    val id: Int?,
    val name: String?,
    val sprite: PokemonSprite?
) : Parcelable

@Parcelize
data class PokemonSprite(
    val imageUrl: String?
) : Parcelable