package com.simplesoftware.pokedexdabia.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.simplesoftware.pokedexdabia.R
import com.simplesoftware.pokedexdabia.domain.models.PokemonDetails
import com.simplesoftware.pokedexdabia.domain.models.PokemonSprite
import com.simplesoftware.pokedexdabia.ui.extensions.toTypeColor
import com.simplesoftware.pokedexdabia.ui.theme.PokedexDaBiaTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private var pokemonClicked: PokemonDetails? = null

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
        val bottomSheetVisible by viewModel.bottomSheetVisible.observeAsState()
        val dropDownExpanded by viewModel.dropDownExpanded.observeAsState()

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
                color = Color(0xFFC7EDFF)
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
                    Header {
                        viewModel.searchPokemonList(it)
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(200.dp),
                        horizontalArrangement = Arrangement.Center,
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        pokemonListDataState?.sortedBy {
                            it.id
                        }?.forEach { pokemon ->
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
                if (bottomSheetVisible == true) ExtraDetailDialogView(pokemonClicked)
                if (dropDownExpanded == true) DialogExample()
            }
        }
    }

    @Composable
    fun DialogExample() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AlertDialog(
                onDismissRequest = { viewModel.closeDropDownMenu() },
                title = { Text("Selecione o tipo") },
                text = {
                    Column {
                        Row {
                            HeaderIcon(resId = R.drawable.charmander_type, text = "Fogo") {}
                            HeaderIcon(resId = R.drawable.squirtle_type, text = "Água") {}
                            HeaderIcon(resId = R.drawable.bulbasaur_type, text = "Planta") { viewModel.loadListPerType("Grass") }
                            HeaderIcon(resId = R.drawable.eevee_type, text = "Normal") { viewModel.loadListPerType("Normal") }
                            HeaderIcon(resId = R.drawable.psyduck, text = "Psiquico") {}
                            HeaderIcon(resId = R.drawable.pikachu_type, text = "Elétrico") {}
                            HeaderIcon(resId = R.drawable.caterpie, text = "Inseto") {}
                            HeaderIcon(resId = R.drawable.dratini, text = "Gelo") {}
                            HeaderIcon(resId = R.drawable.mankey, text = "Lutador") {}
                            HeaderIcon(resId = R.drawable.pidgey, text = "Voador") {}
                        }
                        Row {
                            HeaderIcon(resId = R.drawable.umbreon, text = "Noturno") {}
                            HeaderIcon(resId = R.drawable.venonat, text = "Venenoso") {}
                        }
                    }
                },
                confirmButton = {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 0.dp
                        ),
                        onClick = { viewModel.closeDropDownMenu() }) {
                        Text("Fechar")
                    }
                }
            )
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
                    .wrapContentWidth(Alignment.Start)
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
            Box(Modifier.padding(end = 24.dp)) {
                Row {
                    HeaderIcon(
                        resId = R.drawable.evee,
                        text = "Todos",
                        onClick = {
                            viewModel.cleanList()
                            viewModel.fetchHomeData()
                        }
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    HeaderIcon(
                        resId = R.drawable.fire,
                        text = "Por Tipo",
                        onClick = {
                            viewModel.openDropDownMenu()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun HeaderIcon(
        resId: Int,
        text: String,
        onClick: () -> Unit
    ) {
        Column(
            modifier = Modifier.clickable { onClick() },
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
        pokemonDetails: PokemonDetails,
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        Card(
            border = BorderStroke(0.5.dp, Color.Transparent),
            modifier = Modifier
                .wrapContentWidth()
                .height(200.dp)
                .padding(5.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    pokemonClicked = pokemonDetails
                    viewModel.openDetailDialog()
                    Log.d("POKEMON CLICADO", pokemonDetails.id.toString())
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
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
                    text = "${pokemonDetails.id} - ${pokemonDetails.name}",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    pokemonDetails.types.forEach {
                        PokemonTypeBox(
                            color = it.type?.typeName?.toTypeColor(),
                            typeName = it.type?.typeName
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun PokemonTypeBox(color: Color?, typeName: String?, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                //.wrapContentSize()
                .padding(4.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(20))
                .background(
                    color = color ?: Color.Black,
                    shape = RoundedCornerShape(20)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 1.dp),
                text = typeName ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun ExtraDetailDialogView(
        pokemonDetails: PokemonDetails?
    ) {
        val defaultDominantColor = MaterialTheme.colorScheme.surface
        var dominantColor by remember {
            mutableStateOf(defaultDominantColor)
        }

        Column(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
                .fillMaxHeight(fraction = 0.8f)
                .fillMaxWidth(fraction = 0.6f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(dominantColor, MaterialTheme.colorScheme.surface)
                    ),
                    shape = RoundedCornerShape(10.dp),
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "${pokemonDetails?.id} - ${pokemonDetails?.name}" ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                pokemonDetails?.types?.forEach {
                    PokemonTypeBox(
                        color = it.type?.typeName?.toTypeColor(),
                        typeName = it.type?.typeName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(200.dp)
                            .weight(1f)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                val pokemonWeight = remember {
                    pokemonDetails?.weight?.times(100f)?.let { round(it) / 1000f }
                }

                val pokemonHeight = remember {
                    pokemonDetails?.height?.times(100f)?.let { round(it) / 1000f }
                }


                PokemonAttributesView(
                    iconImageRes = R.drawable.ic_balance,
                    title = "",
                    attributeValue = "$pokemonWeight kg",
                    modifier = Modifier
                )

                Spacer(
                    modifier = Modifier
                        .height(120.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                )

                PokemonAttributesView(
                    iconImageRes = R.drawable.ic_height,
                    title = "",
                    attributeValue = "$pokemonHeight m",
                    modifier = Modifier
                )
            }

            Row(
                modifier = Modifier
                    .height(300.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .weight(1f),
                    model = pokemonDetails?.sprite?.imageUrl,
                    loading = {
                        CircularProgressIndicator(
                            color = Color.Red,
                            modifier = Modifier.scale(0.5f)
                        )
                    },
                    onSuccess = {
                        viewModel.getDominantColor(it.result.drawable) { color ->
                            dominantColor = color
                        }
                    },
                    contentDescription = pokemonDetails?.name,
                    contentScale = ContentScale.Fit
                )

                PokemonBaseStats(
                    pokemonDetails = pokemonDetails,
                    dominantColor = dominantColor,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onClick = { viewModel.closeDetailDialog() },
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = dominantColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Fechar"
                )
            }
        }
    }

    @Composable
    fun PokemonAttributesView(
        iconImageRes: Int,
        title: String,
        attributeValue: String,
        modifier: Modifier
    ) {
        Column(
            modifier = modifier
                .height(120.dp)
                .width(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = iconImageRes),
                contentDescription = ""
            )
            Text(
                modifier = Modifier,
                text = attributeValue,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }
    }

    @Composable
    fun PokemonStatsView(
        statName: String,
        statValue: Int,
        statColor: Color,
        animDuration: Int = 1000,
        animDelay: Int = 0
    ) {
        var animationPlayed by remember {
            mutableStateOf(false)
        }
        val curPercent = animateFloatAsState(
            targetValue = if (animationPlayed) {
                statValue / 100.toFloat()
            } else 0f,
            label = "",
            animationSpec = tween(
                durationMillis = animDuration,
                delayMillis = animDelay
            )
        )
        LaunchedEffect(key1 = true) {
            animationPlayed = true
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(25.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = curPercent.value)
                    .clip(CircleShape)
                    .background(statColor)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = statName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = (curPercent.value * 100).toInt().toString(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun PokemonBaseStats(
        pokemonDetails: PokemonDetails?,
        animDelayPerItem: Int = 100,
        dominantColor: Color,
        modifier: Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            for (i in pokemonDetails!!.stats.indices) {
                val stat = pokemonDetails.stats[i]
                PokemonStatsView(
                    statName = stat.stat?.statName ?: "",
                    statValue = stat.baseValue ?: 0,
                    statColor = dominantColor,
                    animDelay = i * animDelayPerItem
                )
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
            onClick = {}
        )
    }

    @Composable
    @Preview(device = Devices.TABLET, backgroundColor = 1)
    fun ExtraDetailDialogViewPreview() {
        ExtraDetailDialogView(
            PokemonDetails(
                id = 1,
                name = "Bulbasaur",
                sprite = PokemonSprite(
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                ),
                types = emptyList(),
                height = null,
                weight = null,
                stats = emptyList()
            )
        )
    }

    @Composable
    @Preview(device = Devices.TABLET)
    fun AttributesViewPreview() {
        PokemonAttributesView(
            iconImageRes = R.drawable.ic_balance,
            title = "",
            attributeValue = "10.0",
            modifier = Modifier
        )
    }
}