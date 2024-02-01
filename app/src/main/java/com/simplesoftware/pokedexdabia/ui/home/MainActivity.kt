package com.simplesoftware.pokedexdabia.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import com.simplesoftware.pokedexdabia.R
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.ui.theme.PokedexDaBiaTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pokemonListData by viewModel.pokemonListData.observeAsState()
            pokemonListData?.let { HomeScreen() }
        }
    }

    @Composable
    fun HomeScreen() {
        var loadNextPage by remember { mutableStateOf(false) }
        val pokemonListDataState by viewModel.pokemonListData.observeAsState()

        if (loadNextPage) {
            viewModel.loadNextPage()
            loadNextPage = false
        }

        val buttonSize = remember {
            mutableStateOf(80.dp)
        }
//
//        val iconSize = remember {
//            mutableStateOf(12.dp)
//        }

        PokedexDaBiaTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(400.dp),
                    horizontalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(5.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    pokemonListDataState?.forEach { pokemon ->
                        item {
                            SetCard(name = pokemon.name, pokemon.sprite?.imageUrl)
                        }
                    }
                }
                Button(
                    onClick = {
                        buttonSize.value = 120.dp
//                        iconSize.value = 60.dp
                        loadNextPage = true
                    },
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .wrapContentHeight(Alignment.Bottom)
                        .size(buttonSize.value)
                        .padding(end = 12.dp, bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.baseline_arrow_downward
                        ),
                        modifier = Modifier.size(100.dp),
                        contentDescription = "down"
                    )
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
        @PreviewParameter(HomeScreenMock::class) pokemonList: List<PokemonDetails>
    ) {
        pokemonList.forEach {
            SetCard(it.name, it.sprite?.imageUrl)
        }
    }
}