package com.simplesoftware.pokedexdabia.network.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.text.toLowerCase
import androidx.core.graphics.toColor
import com.google.gson.annotations.SerializedName
import com.simplesoftware.pokedexdabia.R

data class HomeResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val nextUrl: String?,
    @SerializedName("previous")
    val previousUrl: String?,
    @SerializedName("results")
    val pokemonList: List<PokemonResponse>?
)

data class PokemonResponse(
    @SerializedName("url")
    val url: String?
)

data class PokemonDetailsResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sprites")
    val sprite: PokemonSpriteResponse?,
    @SerializedName("types")
    val types: List<PokemonTypesResponse>?
)

data class PokemonSpriteResponse(
    @SerializedName("front_default")
    val imageUrl: String?
)

data class PokemonTypesResponse(
    @SerializedName("type")
    val type: PokemonTypeResponse?
)

data class PokemonTypeResponse(
    @SerializedName("name")
    val typeName: String?,
)

fun String.toTypeColor(): Color {
    return when (this) {
        PokemonTypeNames.GRASS -> Color.Green
        PokemonTypeNames.POISON -> Color.Magenta
        PokemonTypeNames.FIRE -> Color.Red
        PokemonTypeNames.WATER -> Color.Blue
        PokemonTypeNames.NORMAL -> Color.Gray
        PokemonTypeNames.BUG -> Color(0xFF32CD32)
        PokemonTypeNames.ELECTRIC -> Color.Yellow
        PokemonTypeNames.GROUND -> Color(0xFF8B4513)
        PokemonTypeNames.FLYING -> Color(0xFF87CEEB)
        PokemonTypeNames.FAIRY -> Color(0xFFFFC0CB)
        PokemonTypeNames.GHOST -> Color(0xFF9370DB)
        PokemonTypeNames.DRAGON -> Color(0xFFC8A2C8)
        PokemonTypeNames.PSYCHIC -> Color(0xFFFF69B4)
        else -> Color.Black
    }
}