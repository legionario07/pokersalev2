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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val scrollBehavior =
                TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

            setupInitNavHostController()
            val listItems = remember {
                mutableSetOf<TabItem>()
            }
            PokerSaleV2DomainTheme(darkTheme = false) {

                val currentRoute = currentRoute(navController = navController)
                builderIcons(listItems, currentRoute == ScreenEnum.LOGIN)

                Scaffold(
                    topBar = {
                        if (currentRoute.isTopBar) {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.background,
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
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = EMPTY_STRING
                                        )
                                    }
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                    },
                    bottomBar = {
                        if (currentRoute.isShowBottomBar) {
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
    private fun setupInitNavHostController() {
        navController = rememberNavController()
    }

    private fun getStartRoute(): ScreenEnum {
        return ScreenEnum.LOGIN
    }

    @Composable
    private fun builderIcons(
        listItems: MutableSet<TabItem>,
        isLoginScreen: Boolean,
    ) {

        if (isLoginScreen.not()) {
            listItems.add(
                TabItem(
                    route = ScreenEnum.HOME,
                    icon = Icons.Default.Home,
                )
            )

            if (Session.player?.isAdmin == true) {

                listItems.add(
                    TabItem(
                        route = ScreenEnum.REGISTER_PLAYER,
                        icon = Icons.Default.PersonAdd,
                    )
                )
                listItems.add(
                    TabItem(
                        route = ScreenEnum.BALANCE,
                        icon = Icons.Default.MonetizationOn,
                    )
                )
                listItems.add(
                    TabItem(
                        route = ScreenEnum.REGISTER_MATCH_DATA_FOR_ENTRY,
                        icon = Icons.Default.Add,
                    )
                )
            }
        }
    }
}

