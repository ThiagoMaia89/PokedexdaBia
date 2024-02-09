package com.simplesoftware.pokedexdabia.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplesoftware.pokedexdabia.domain.Remote
import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import kotlinx.coroutines.launch
import androidx.palette.graphics.Palette
import com.simplesoftware.pokedexdabia.domain.models.PokemonType
import com.simplesoftware.pokedexdabia.domain.models.PokemonTypes
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val remote: Remote
) : ViewModel() {

    private val _homeData = MutableLiveData<Home>()
    private val homeData: LiveData<Home> get() = _homeData

    private val _detailsData = MutableLiveData<PokemonDetails>()
    private val detailsData: LiveData<PokemonDetails> get() = _detailsData

    private val _pokemonListData = MutableLiveData<List<PokemonDetails>>()
    val pokemonListData: LiveData<List<PokemonDetails>> get() = _pokemonListData

    private val _bottomSheetVisible = MutableLiveData(false)
    val bottomSheetVisible: LiveData<Boolean> get() = _bottomSheetVisible

    private val _dropDownExpanded = MutableLiveData(false)
    val dropDownExpanded: LiveData<Boolean> get() = _dropDownExpanded

    private var pokemonList = mutableListOf<PokemonDetails>()

    private var cachedPokemonList = listOf<PokemonDetails>()
    private var isSearchStarting = true

    init {
        fetchHomeData()
    }

    fun fetchHomeData() {
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

    fun loadNextPage() {
        val url = homeData.value?.nextUrl ?: ""
        viewModelScope.launch {
            try {
                _homeData.value = remote.getNextData(url)
                homeData.value?.pokemonList?.forEach { pokemon ->
                    pokemon.url?.let { url ->
                        _detailsData.value = remote.getPokemonDetails(url)
                        detailsData.value?.let {
                            pokemonList.add(it)
                        }
                    }
                }
                _pokemonListData.value = pokemonList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadListPerType(typeName: String) {
        val pokemonTypeList : MutableList<PokemonTypes> = emptyList<PokemonTypes>().toMutableList()
        pokemonList.forEach {
            it.types.forEach { types ->
                pokemonTypeList.add(types)
            }
        }
        val pokemonListToShow = pokemonTypeList.filter {
            it.type?.typeName!!.contains(typeName, ignoreCase = true)
        }

        _pokemonListData.value = pokemonList.filter {
            it.types.contains(pokemonListToShow.first())
        }
    }

    fun cleanList() {
        _pokemonListData.value = emptyList()
        pokemonList.clear()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonListData.value
        } else {
            cachedPokemonList
        }
        if (query.isEmpty()) {
            _pokemonListData.value = cachedPokemonList.toMutableList()
            isSearchStarting = true
        }
        val results = listToSearch?.filter {
            it.name!!.startsWith(
                query.trim(),
                ignoreCase = true
            ) || it.id.toString() == query.trim()
        }
        if (isSearchStarting) {
            cachedPokemonList = pokemonList
            isSearchStarting = false
        }
        _pokemonListData.value = results?.toMutableList()!!
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    fun openDetailDialog() {
        _bottomSheetVisible.value = true
    }

    fun closeDetailDialog() {
        _bottomSheetVisible.value = false
    }

    fun openDropDownMenu() {
        _dropDownExpanded.value = true
    }

    fun closeDropDownMenu() {
        _dropDownExpanded.value = false
    }
}