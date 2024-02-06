package com.simplesoftware.pokedexdabia.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.glide.rememberGlidePainter
import com.simplesoftware.pokedexdabia.R
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.ui.extensions.toTypeColor
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
                color = Color(0x204169E1)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Pokedex",
                        modifier = Modifier
                            .width(190.dp)
                            .height(100.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Inside
                    )
                    Header()
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(200.dp),
                        horizontalArrangement = Arrangement.Center,
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        pokemonListDataState?.forEach { pokemon ->
                            item {
                                PokemonCard(pokemonDetails = pokemon)
                            }
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
    fun Header(
        onSearch: (String) -> Unit = {}
    ) {
        var text by remember {
            mutableStateOf("")
        }
        var isHintDisplayed by remember {
            mutableStateOf(text == "")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(800.dp)
                    .padding(16.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onSearch(it)
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 5.dp, shape = CircleShape)
                        .background(Color.White, CircleShape)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .onFocusChanged {
                            isHintDisplayed = !it.isFocused
                        }
                )
                if (isHintDisplayed) {
                    Text(
                        text = "Buscar...",
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
            }
            HeaderIcon(
                resId = R.drawable.charmander,
                text = "Por tipo",
                modifier = Modifier.clickable {
                    TODO()
                }
            )
            HeaderIcon(
                resId = R.drawable.dratini,
                text = "Por temporada",
                modifier = Modifier.clickable {
                    TODO()
                }
            )
            HeaderIcon(
                resId = R.drawable.squirtle,
                text = "Por Região",
                modifier = Modifier.clickable {
                    TODO()
                }
            )
            HeaderIcon(
                resId = R.drawable.evee,
                text = "Meus Favoritos",
                modifier = Modifier.clickable {
                    TODO()
                }
            )
        }
    }

    @Composable
    fun HeaderIcon(
        resId: Int,
        text: String,
        modifier: Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = "IconHeader",
                contentScale = ContentScale.Inside
            )
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun PokemonCard(
        pokemonDetails: PokemonDetails
    ) {
        Card(
            border = BorderStroke(0.5.dp, Color.Transparent),
            modifier = Modifier
                .wrapContentWidth()
                .height(200.dp)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp,
                pressedElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SubcomposeAsyncImage(
                    model = pokemonDetails.sprite?.imageUrl,
                    loading = {
                        CircularProgressIndicator(
                            color = Color.Red,
                            modifier = Modifier.scale(0.5f)
                        )
                    },
                    contentDescription = pokemonDetails.name,
                    modifier = Modifier
                        .fillMaxSize(0.5f),
                    contentScale = ContentScale.Inside
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = pokemonDetails.name ?: "",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    pokemonDetails.types.forEach {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(4.dp)
                                .shadow(elevation = 5.dp, shape = RoundedCornerShape(20))
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
    fun HeaderPreview() {
        Header()
    }

    @Composable
    @Preview(device = Devices.TABLET)
    fun HeaderIconPreview() {
        HeaderIcon(
            resId = R.drawable.evee,
            text = "Eevee",
            modifier = Modifier
        )
    }
}