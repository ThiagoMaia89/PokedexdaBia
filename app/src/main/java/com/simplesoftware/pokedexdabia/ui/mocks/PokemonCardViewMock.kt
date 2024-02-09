package com.simplesoftware.pokedexdabia.ui.mocks

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.Pokemon
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.domain.models.PokemonSprite
import com.simplesoftware.pokedexdabia.domain.models.PokemonType
import com.simplesoftware.pokedexdabia.domain.models.PokemonTypes

class PokemonCardViewMock : PreviewParameterProvider<PokemonDetails> {

    override val values: Sequence<PokemonDetails>
        get() {
            return sequenceOf(
                PokemonDetails(
                    id = null,
                    name = "bulbasaur",
                    sprite = PokemonSprite(
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                    ),
                    types = listOf(
                        PokemonTypes(
                            type = PokemonType(
                                typeName = "Grass"
                            )
                        ),
                        PokemonTypes(
                            type = PokemonType(
                                typeName = "Grass"
                            )
                        ),
                        PokemonTypes(
                            type = PokemonType(
                                typeName = "Grass"
                            )
                        ),
                        PokemonTypes(
                            type = PokemonType(
                                typeName = "Grass"
                            )
                        ),
                    ),
                    height = null,
                    weight = null,
                    stats = emptyList()
                )
            )
        }


}