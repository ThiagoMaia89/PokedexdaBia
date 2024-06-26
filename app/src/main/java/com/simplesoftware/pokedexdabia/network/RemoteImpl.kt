package com.simplesoftware.pokedexdabia.network

import com.simplesoftware.pokedexdabia.domain.Remote
import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.Pokemon
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.network.networkConfig.ApiService
import java.lang.Exception
import com.simplesoftware.pokedexdabia.network.mappers.mapToHome
import com.simplesoftware.pokedexdabia.network.mappers.mapToPokemonDetails
import com.simplesoftware.pokedexdabia.network.models.PokemonResponse

class RemoteImpl(private val apiService: ApiService) : Remote {

    override suspend fun getHomeData(): Home {
        try {
            val response = apiService.getHomeData()
            if (response.isSuccessful) {
                val homeResponse = response.body()
                return homeResponse?.mapToHome() ?: throw IllegalStateException("Body is null")
            } else {
                throw throw IllegalStateException("Network request failed with code: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("Error fetching data", e)
        }
    }

    override suspend fun getNextData(url: String): Home {
        try {
            val response = apiService.getNextPage(url)
            if (response.isSuccessful) {
                val nextPageData = response.body()
                return nextPageData?.mapToHome() ?: throw IllegalStateException("Body is null")
            } else {
                throw throw IllegalStateException("Network request failed with code: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("Error fetching data", e)
        }
    }

    override suspend fun getPokemonDetails(url: String): PokemonDetails {
        try {
            val response = apiService.getPokemonDetails(url)
            if (response.isSuccessful) {
                val detailsResponse = response.body()
                return detailsResponse?.mapToPokemonDetails()
                    ?: throw IllegalStateException("Body is null")
            } else {
                throw throw IllegalStateException("Network request failed with code: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("Error fetching data", e)
        }
    }

    override suspend fun getPokemonDetailsByType(number: String): Home {
        try {
            val response = apiService.getPokemonByType(number)
            if (response.isSuccessful) {
                val homeResponse = response.body()
                return homeResponse?.mapToHome() ?: throw IllegalStateException("Boddy is null")
            } else {
                throw throw IllegalStateException("Network request failed with code: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("Error fetching data", e)
        }
    }
}