package com.simplesoftware.pokedexdabia.network.mappers

import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.Pokemon
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.domain.models.PokemonSprite
import com.simplesoftware.pokedexdabia.network.models.HomeResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonDetailsResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonSpriteResponse

fun HomeResponse.mapToHome() =
    Home(
        count = count,
        nextUrl = nextUrl,
        previousUrl = previousUrl,
        pokemonList = pokemonList?.map { it.mapToPokemon() } ?: emptyList()
    )

private fun PokemonResponse.mapToPokemon() =
    Pokemon(
        url = url
    )

fun PokemonDetailsResponse.mapToPokemonDetails() =
    PokemonDetails(
        id = id,
        name = name,
        sprite = sprite?.mapToPokemonSprite()
    )

private fun PokemonSpriteResponse.mapToPokemonSprite() =
    PokemonSprite(
        imageUrl = imageUrl
    )