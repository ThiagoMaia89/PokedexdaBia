package com.simplesoftware.pokedexdabia.ui.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.Pokemon

class HomeScreenMock : PreviewParameterProvider<Home> {

    override val values: Sequence<Home>
        get() {
            return sequenceOf(
                Home(
                    count = null,
                    nextUrl = null,
                    previousUrl = null,
                    pokemonList = listOf(
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/1/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/2/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/3/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/4/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/5/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/6/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/7/"
                        ),
                        Pokemon(
                            url = "https://pokeapi.co/api/v2/pokemon/8/"
                        )
                    )
                )
            )
        }


}