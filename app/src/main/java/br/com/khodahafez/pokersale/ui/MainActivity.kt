@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.khodahafez.pokersale.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.khodahafez.pokersale.navigation.ScreenEnum
import br.com.khodahafez.pokersale.navigation.navigationGraph
import br.com.khodahafez.pokersale.ui.ui.theme.PokerSaleV2DomainTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setupInitNavHostController()
            PokerSaleV2DomainTheme(darkTheme = false) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        navigationGraph(
                            navController = navController,
                            startDestination = getStartRoute()
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
}

