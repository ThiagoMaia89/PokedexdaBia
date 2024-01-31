package com.simplesoftware.pokedexdabia.domain

import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails

interface Remote {
    suspend fun getHomeData(): Home
    suspend fun getPokemonDetails(url: String): PokemonDetails
}