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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.model.PlayerHelper
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
import br.com.khodahafez.pokersale.ui.utils.showToast

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory()),
) {
    val stateUI by viewModel.homeStateUI.collectAsState()
    val context = LocalContext.current
    val playersHelpers = remember {
        mutableListOf<PlayerHelper>()
    }

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is HomeStateUI.InitialState -> {
            // Do Noting
        }

        is HomeStateUI.GetAllSuccessful -> {
            playersHelpers.addAll(result.listPlayerHelper)
            loading = false
        }

        is HomeStateUI.Loading -> {
            loading = true
        }

        is HomeStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(mediumDimens.size08),
            shape = RoundedCornerShape(mediumDimens.size10),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
            ),
            elevation = CardDefaults.cardElevation(mediumDimens.size04),
            border = BorderStroke(width = mediumDimens.size01, color = Color.LightGray)
        ) {
            LazyColumn {
                items(
                    playersHelpers
                ) { it ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(mediumDimens.size70)
                            .padding(mediumDimens.size06),
                        shape = RoundedCornerShape(mediumDimens.size06),
                        elevation = CardDefaults.cardElevation(mediumDimens.size04)
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
                                    .padding(start = mediumDimens.size30),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = playersHelpers.indexOf(it).inc().toString(),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxHeight()
                                    .padding(start = mediumDimens.size30),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = it.player.name,
                                    modifier = Modifier
                                        .padding(bottom = mediumDimens.size02)
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
                                    .padding(end = mediumDimens.size30),
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

data class CategoryPlayersHelper(
    val stickerTitle: String,

)
