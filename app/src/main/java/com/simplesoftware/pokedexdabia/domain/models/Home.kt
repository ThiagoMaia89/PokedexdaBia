package com.simplesoftware.pokedexdabia.domain.models

import android.os.Parcelable
import com.simplesoftware.pokedexdabia.network.models.PokemonByTypeListResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    val count: Int?,
    val nextUrl: String?,
    val previousUrl: String?,
    val pokemonList: List<Pokemon>,
    val pokemonByTypeList: List<PokemonByTypeList>
) : Parcelable

@Parcelize
data class PokemonByTypeList(
    val pokemonByType: Pokemon?
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
    val types: List<PokemonTypes>,
    val height: Int?,
    val weight: Int?,
    val stats: List<PokemonStatsList>
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

@Parcelize
data class PokemonStatsList(
    val baseValue: Int?,
    val stat: PokemonStat?
) : Parcelable

@Parcelize
data class PokemonStat(
    val statName: String?
) : Parcelable