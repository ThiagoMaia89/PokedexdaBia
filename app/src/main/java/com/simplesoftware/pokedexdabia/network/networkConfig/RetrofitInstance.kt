package com.simplesoftware.pokedexdabia.network.networkConfig

import com.simplesoftware.pokedexdabia.network.UrlsConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(UrlsConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }

}