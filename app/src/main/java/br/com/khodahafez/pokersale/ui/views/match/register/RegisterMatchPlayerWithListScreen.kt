@file:OptIn(ExperimentalFoundationApi::class)

package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatch
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatchHeader
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchViewModelFactory

@Composable
fun RegisterMatchPlayerWithListScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterMatchViewModel = viewModel(factory = RegisterMatchViewModelFactory()),
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFdefcfc)
            )
    ) {

        val context = LocalContext.current

        var loading by rememberSaveable {
            mutableStateOf(true)
        }

        val players = remember {
            mutableStateListOf<RegisterMatchScreenModel>()
        }

        val uiState by remember {
            viewModel.stateUI
        }.collectAsState()

        when (val result = uiState) {
            is RegisterMatchStateUI.InitialState,
            is RegisterMatchStateUI.Loading -> {
                // Do Nothing
            }

            is RegisterMatchStateUI.Error -> {
                context.showToast(result.message)
            }

            is RegisterMatchStateUI.GetAllUsersState -> {
                loading = false
                players.addAll(result.players.map {
                    RegisterMatchScreenModel(player = it)
                })
            }

            is RegisterMatchStateUI.SaveSuccessful -> {
//                onSaveMatchSuccessful()
                viewModel.clearStateUI()
            }
        }

        CircularLoading(isLoading = loading)

        if (loading.not()) {
            RowPlayerInNewMatchHeader()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    players.toList(),
                    key = { playerItem ->
                        playerItem.player.login
                    }
                ) { item ->

                    RowPlayerInNewMatch(
                        player = item
                    )
                }
            }
        }
    }
}
