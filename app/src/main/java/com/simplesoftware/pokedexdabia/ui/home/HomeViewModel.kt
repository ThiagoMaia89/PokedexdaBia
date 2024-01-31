package com.simplesoftware.pokedexdabia.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.simplesoftware.pokedexdabia.domain.Remote
import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.network.RemoteImpl
import com.simplesoftware.pokedexdabia.network.networkConfig.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remote: Remote = RemoteImpl(RetrofitInstance.apiService)
) : ViewModel() {

    private val _homeData = MutableLiveData<Home>()
    private val homeData: LiveData<Home> get() = _homeData

    private val _detailsData = MutableLiveData<PokemonDetails>()
    private val detailsData: LiveData<PokemonDetails> get() = _detailsData

    private val _pokemonListData = MutableLiveData<List<PokemonDetails>>()
    val pokemonListData: LiveData<List<PokemonDetails>> get() = _pokemonListData

    private val pokemonList = mutableListOf<PokemonDetails>()

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            try {
                _homeData.value = remote.getHomeData()
                homeData.value?.pokemonList?.forEach { pokemon ->
                    pokemon.url?.let { url ->
                        _detailsData.value = remote.getPokemonDetails(url)
                        detailsData.value?.let { pokemonList.add(it) }
                    }
                }
                _pokemonListData.value = pokemonList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}