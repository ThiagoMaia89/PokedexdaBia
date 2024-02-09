package com.simplesoftware.pokedexdabia.network.networkConfig

import com.simplesoftware.pokedexdabia.network.UrlsConstants
import com.simplesoftware.pokedexdabia.network.models.HomeResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonDetailsResponse
import com.simplesoftware.pokedexdabia.network.models.PokemonResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @GET(UrlsConstants.POKEMON)
    suspend fun getHomeData(): Response<HomeResponse>

    @GET
    suspend fun getNextPage(@Url url: String): Response<HomeResponse>

    @GET
    suspend fun getPokemonDetails(@Url url: String): Response<PokemonDetailsResponse>

    @GET("${UrlsConstants.BASE_POKEMON_PER_TYPE}/{number}")
    suspend fun getPokemonByType(@Path("number") number: String) : Response<HomeResponse>

}