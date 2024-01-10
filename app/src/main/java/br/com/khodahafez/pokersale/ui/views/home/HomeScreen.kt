@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.khodahafez.pokersale.ui.views.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.navigation.ScreenEnum
import br.com.khodahafez.pokersale.ui.model.PlayerHelper
import br.com.khodahafez.pokersale.ui.views.tabbar.PokerSaleBottomNavigation
import br.com.khodahafez.pokersale.ui.views.tabbar.TabItem

private val players = mutableListOf(
    PlayerHelper(
        Player(
            id = "1I12S23SS2",
            name = "Paulo Dias"
        ),
        pointsTotal = 100,
        bounties = 10
    ),
    PlayerHelper(
        Player(
            id = "1I1223SS2",
            name = "Teste Jogador"
        ),
        pointsTotal = 12,
        bounties = 11
    ),
    PlayerHelper(
        Player(
            id = "1I1223SS2",
            name = "Teste Jogador 2"
        ),
        pointsTotal = 122,
        bounties = 1
    ),
    PlayerHelper(
        Player(
            id = "1I1223SS21",
            name = "Teste Jogador 3"
        ),
        pointsTotal = 142,
        bounties = 2
    )
)

@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    val listItems = remember {
        mutableListOf(
            TabItem(
                title = "Home",
                route = ScreenEnum.HOME,
                icon = Icons.Default.Home,
            ),
        )
    }

    LaunchedEffect(key1 = Unit) {
        if (Session.player?.isAdmin == true) {
            listItems.addAll(
                listOf(
                    TabItem(
                        title = "Jogador",
                        route = ScreenEnum.REGISTER_PLAYER,
                        icon = Icons.Default.PersonAdd,
                    ),
                    TabItem(
                        title = "Caixa",
                        route = ScreenEnum.BALANCE,
                        icon = Icons.Default.MonetizationOn,
                    ),
                    TabItem(
                        title = "Partida",
                        route = ScreenEnum.REGISTER_MATCH_DATA_FOR_ENTRY,
                        icon = Icons.Default.Add,
                    ),
                )
            )
        }
    }

    Scaffold(
        bottomBar = {
            PokerSaleBottomNavigation(
                navController = navHostController,
                items = listItems
            )
        },
    ) { _ ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = 42.dp
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(9.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                border = BorderStroke(width = 1.dp, color = Color.LightGray)
            ) {
                LazyColumn {
                    items(players) { it ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(6.dp),
                            shape = RoundedCornerShape(6.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .fillMaxHeight()
                                        .padding(start = 30.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = players.indexOf(it).inc().toString(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .fillMaxHeight()
                                        .padding(start = 30.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = it.player.name,
                                        modifier = Modifier
                                            .padding(bottom = 2.dp)
                                            .align(Alignment.Start),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    Text(
                                        text = "${it.bounties} bounties",
                                        Modifier.align(Alignment.Start),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(end = 30.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "${it.pointsTotal} pontos",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}