package com.simplesoftware.pokedexdabia.domain.models

import android.os.Parcelable
import com.simplesoftware.pokedexdabia.network.models.PokemonTypeResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonTypesResponse
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
    val sprite: PokemonSprite?,
    val types: List<PokemonTypes>
) : Parcelable

@Parcelize
data class PokemonSprite(
    val imageUrl: String?
) : Parcelable

@Parcelize
data class PokemonTypes(
    val type: PokemonType?
) : Parcelable

@Parcelize
data class PokemonType(
    val typeName: String?
) : Parcelable