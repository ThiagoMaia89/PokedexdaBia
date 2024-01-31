package com.simplesoftware.pokedexdabia.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import com.simplesoftware.pokedexdabia.domain.models.Home
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.ui.theme.PokedexDaBiaTheme

@Composable
fun HomeScreen(
    pokemonList: List<PokemonDetails>
) {
    PokedexDaBiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                pokemonList.forEach { pokemon ->
                    item {
                        SetCard(name = pokemon.name, pokemon.sprite?.imageUrl)
                    }
                }
            }
        }
    }
}

@Composable
internal fun SetCard(
    name: String?,
    imageUrl: String?
) {
    Card(
        border = BorderStroke(0.5.dp, Color.Red),
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .padding(5.dp)
    ) {
        val painter = rememberGlidePainter(request = imageUrl)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Text(text = name ?: "")
        }
    }
}

@Composable
@Preview(device = Devices.TABLET)
fun HomePreview(
    @PreviewParameter(HomeScreenMock::class)
    pokemonList: List<PokemonDetails>
) {
    HomeScreen(pokemonList)
}