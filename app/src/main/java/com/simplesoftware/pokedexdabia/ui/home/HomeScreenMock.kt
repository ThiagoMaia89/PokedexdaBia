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
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        ),
                        Pokemon(
                            url = null
                        )
                    )
                )
            )
        }


}