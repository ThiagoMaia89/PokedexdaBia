package com.simplesoftware.pokedexdabia.ui.extensions

import androidx.compose.ui.graphics.Color
import com.simplesoftware.pokedexdabia.network.models.PokemonTypeNames

fun String.toTypeColor(): Color {
    return when (this) {
        PokemonTypeNames.GRASS -> Color(0xFF44F644)
        PokemonTypeNames.POISON -> Color.Magenta
        PokemonTypeNames.FIRE -> Color(0xFFFA7D00)
        PokemonTypeNames.WATER -> Color(0xFF3CA4FF)
        PokemonTypeNames.NORMAL -> Color.Gray
        PokemonTypeNames.BUG -> Color(0xFF8EB745)
        PokemonTypeNames.ELECTRIC -> Color.Yellow
        PokemonTypeNames.GROUND -> Color(0xFF97604C)
        PokemonTypeNames.FLYING -> Color(0xFF87CEEB)
        PokemonTypeNames.FAIRY -> Color(0xFFFFC0CB)
        PokemonTypeNames.GHOST -> Color(0xFF9370DB)
        PokemonTypeNames.DRAGON -> Color(0xFFC8A2C8)
        PokemonTypeNames.PSYCHIC -> Color(0xFFFF69B4)
        PokemonTypeNames.FIGHTING -> Color(0xFFB73413)
        PokemonTypeNames.DARK -> Color(0xFF5B3223)
        PokemonTypeNames.ROCK -> Color(0xFF98773D)
        PokemonTypeNames.ICE -> Color(0xFF7BE2C7)
        PokemonTypeNames.STEEL -> Color(0xFFB7CEC6)
        else -> Color.Black
    }
}

fun String.capitalizeFirstLetter(): String {
    return this.first().uppercase().plus(this.substring(1))
}