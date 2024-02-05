package com.simplesoftware.pokedexdabia.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import com.simplesoftware.pokedexdabia.R
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.domain.models.PokemonTypes
import com.simplesoftware.pokedexdabia.network.models.toTypeColor
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
        val interactionSource = remember { MutableInteractionSource() }

        if (loadNextPage) {
            viewModel.loadNextPage()
            loadNextPage = false
        }

        val buttonSize = remember {
            mutableStateOf(100.dp)
        }

        val ballImage: Painter = painterResource(id = R.drawable.pokeball)

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
                            SetCard(name = pokemon.name, pokemon.sprite?.imageUrl, pokemon.types)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .wrapContentHeight(Alignment.Bottom)
                        .size(buttonSize.value)
                        .background(Color.Transparent, shape = CircleShape)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            buttonSize.value = 160.dp
                            loadNextPage = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = ballImage,
                        contentDescription = "down",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }

    @Composable
    internal fun SetCard(
        name: String?,
        imageUrl: String?,
        typeList: List<PokemonTypes>?
    ) {
        Card(
            border = BorderStroke(0.5.dp, Color.Red),
            modifier = Modifier
                .wrapContentWidth()
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
                        .fillMaxSize(0.5f),
                    contentScale = ContentScale.Inside
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = name ?: "",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    typeList?.forEach {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(4.dp)
                                .background(
                                    color = it.type?.typeName?.toTypeColor() ?: Color.Black,
                                    shape = RoundedCornerShape(20)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 1.dp),
                                text = it.type?.typeName ?: "",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    @Preview(device = Devices.TABLET)
    fun HomePreview(
        @PreviewParameter(HomeScreenMock::class) pokemonList: List<PokemonDetails>
    ) {
        pokemonList.forEach {
            SetCard(it.name, it.sprite?.imageUrl, it.types)
        }
    }
}