@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.khodahafez.pokersale.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.navigation.ScreenEnum
import br.com.khodahafez.pokersale.navigation.navigationGraph
import br.com.khodahafez.pokersale.ui.ui.theme.PokerSaleV2DomainTheme
import br.com.khodahafez.pokersale.ui.views.components.currentRoute
import br.com.khodahafez.pokersale.ui.views.tabbar.PokerSaleBottomNavigation
import br.com.khodahafez.pokersale.ui.views.tabbar.TabItem


@OptIn(ExperimentalMaterial3Api::class)
class PokerSaleActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SetupInitNavHostController()
            val listItems = remember {
                mutableSetOf<TabItem>()
            }
            PokerSaleV2DomainTheme(darkTheme = false) {

                val currentRoute = currentRoute(navController = navController)
                BuilderIcons(listItems, currentRoute == ScreenEnum.LOGIN)

                val scrollBehavior =
                    TopAppBarDefaults.enterAlwaysScrollBehavior()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        if (currentRoute.isTopBar && Session.player != null) {
                            TopAppBar(
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = Color(0xFF333399),
                                    titleContentColor = Color.White,
                                ),
                                title = {
                                    Text(
                                        text = currentRoute.displayName,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 13.sp
                                    )
                                },
                                navigationIcon = {
                                    if (currentRoute.route != ScreenEnum.HOME.route) {
                                        IconButton(onClick = { navController.navigateUp() }) {
                                            Icon(
                                                tint = Color.White,
                                                imageVector = Icons.Filled.ArrowBack,
                                                contentDescription = EMPTY_STRING
                                            )
                                        }
                                    }
                                },
                                actions = {
                                    if (Session.player!!.isAdmin) {
                                        IconButton(onClick = { navController.navigate(ScreenEnum.RANKING_BALANCE.route) }) {
                                            Icon(
                                                tint = Color.White,
                                                imageVector = Icons.Filled.AutoGraph,
                                                contentDescription = EMPTY_STRING
                                            )
                                        }

                                        IconButton(onClick = { navController.navigate(ScreenEnum.REGISTER_MATCH_DATA_FOR_ENTRY.route) }) {
                                            Icon(
                                                tint = Color.White,
                                                imageVector = Icons.Default.Add,
                                                contentDescription = EMPTY_STRING
                                            )
                                        }
                                        IconButton(onClick = { navController.navigate(ScreenEnum.REGISTER_PLAYER.route) }) {
                                            Icon(
                                                tint = Color.White,
                                                imageVector = Icons.Default.PersonAdd,
                                                contentDescription = EMPTY_STRING
                                            )
                                        }
                                        IconButton(onClick = { navController.navigate(ScreenEnum.NEW_BALANCE_POKER_SALE.route) }) {
                                            Icon(
                                                tint = Color.White,
                                                imageVector = Icons.Default.MonetizationOn,
                                                contentDescription = EMPTY_STRING
                                            )
                                        }
                                    }
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            tint = Color.White,
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = EMPTY_STRING
                                        )
                                    }
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                    },
                    bottomBar = {
                        if (currentRoute.isShowBottomBar && Session.player != null) {
                            PokerSaleBottomNavigation(
                                navController = navController,
                                items = listItems
                            )
                        }
                    },
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding()
                            )
                    ) {
                        navigationGraph(
                            navController = navController, startDestination = getStartRoute()
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun SetupInitNavHostController() {
        navController = rememberNavController()
    }

    private fun getStartRoute(): ScreenEnum {
        return ScreenEnum.LOGIN
    }

    @Composable
    private fun BuilderIcons(
        listItems: MutableSet<TabItem>,
        isLoginScreen: Boolean,
    ) {

        if (isLoginScreen.not() && Session.player != null) {
            listItems.add(
                TabItem(
                    route = ScreenEnum.HOME,
                    icon = Icons.Default.Home,
                )
            )

            listItems.add(
                TabItem(
                    route = ScreenEnum.MY_BALANCE,
                    icon = Icons.Default.Money,
                )
            )

            listItems.add(
                TabItem(
                    route = ScreenEnum.BALANCE_POKER_SALE,
                    icon = Icons.Default.AttachMoney,
                )
            )
        }
    }
}

