package com.simplesoftware.pokedexdabia.network.mappers

import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.Pokemon
import com.simplesoftware.pokedexdabia.domain.models.PokemonByTypeList
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.domain.models.PokemonSprite
import com.simplesoftware.pokedexdabia.domain.models.PokemonStat
import com.simplesoftware.pokedexdabia.domain.models.PokemonStatsList
import com.simplesoftware.pokedexdabia.domain.models.PokemonType
import com.simplesoftware.pokedexdabia.domain.models.PokemonTypes
import com.simplesoftware.pokedexdabia.network.models.HomeResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonByTypeListResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonDetailsResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonSpriteResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonStatResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonStatsListResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonTypeResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonTypesResponse
import com.simplesoftware.pokedexdabia.ui.extensions.capitalizeFirstLetter

fun HomeResponse.mapToHome() =
    Home(
        count = count,
        nextUrl = nextUrl,
        previousUrl = previousUrl,
        pokemonList = pokemonList?.map { it.mapToPokemon() } ?: emptyList(),
        pokemonByTypeList = pokemonByTypeList?.map { it.mapToPokemonByTypeList() } ?: emptyList()
    )

private fun PokemonByTypeListResponse.mapToPokemonByTypeList() =
    PokemonByTypeList(
        pokemonByType = pokemonByType?.mapToPokemon()
    )

private fun PokemonResponse.mapToPokemon() =
    Pokemon(
        url = url
    )

fun PokemonDetailsResponse.mapToPokemonDetails() =
    PokemonDetails(
        id = id,
        name = name?.capitalizeFirstLetter(),
        sprite = sprite?.mapToPokemonSprite(),
        types = types?.map { it.mapToPokemonTypes() } ?: emptyList(),
        height = height,
        weight = weight,
        stats = stats?.map { it.mapToPokemonStatsList() } ?: emptyList()
    )

private fun PokemonSpriteResponse.mapToPokemonSprite() =
    PokemonSprite(
        imageUrl = imageUrl
    )

private fun PokemonTypesResponse.mapToPokemonTypes() =
    PokemonTypes(
        type = type?.mapToPokemonType()
    )

private fun PokemonTypeResponse.mapToPokemonType() =
    PokemonType(
        typeName = typeName?.capitalizeFirstLetter()
    )

private fun PokemonStatsListResponse.mapToPokemonStatsList() =
    PokemonStatsList(
        baseValue = baseValue,
        stat = stat?.mapToPokemonStat()
    )

private fun PokemonStatResponse.mapToPokemonStat() =
    PokemonStat(
        statName = statName
    )