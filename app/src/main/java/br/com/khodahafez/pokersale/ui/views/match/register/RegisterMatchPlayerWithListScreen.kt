package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.components.ButtonRounded
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatch
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatchHeader
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchViewModelFactory

@Composable
fun RegisterMatchPlayerWithListScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterMatchViewModel = viewModel(
        factory = RegisterMatchViewModelFactory(
            context = LocalContext.current
        )
    ),
    idMatchCreated: String,
    onSaveMatchSuccessful: () -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.getMatchById(idMatchCreated)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFdefcfc)
            )
    ) {

        val scrollState = rememberScrollState()

        val context = LocalContext.current

        var loading by rememberSaveable {
            mutableStateOf(true)
        }

        var hasMinNumberPlayers by rememberSaveable {
            mutableStateOf(false)
        }

        val players = remember {
            mutableStateListOf<RegisterMatchScreenModel>()
        }

        val uiState by remember {
            viewModel.stateUI
        }.collectAsState()

        when (val result = uiState) {
            is RegisterMatchStateUI.InitialState,
            is RegisterMatchStateUI.GetPlayerRuntimeState,
            is RegisterMatchStateUI.Loading -> {
                // Do Nothing
            }

            is RegisterMatchStateUI.Error -> {
                context.showToast(result.message)
            }

            is RegisterMatchStateUI.GetAllUsersState -> {
                if (loading) {
                    loading = false
                    players.addAll(result.players.map {
                        RegisterMatchScreenModel(player = it)
                    })
                }
            }

            is RegisterMatchStateUI.SaveSuccessful -> {
                onSaveMatchSuccessful()
                viewModel.clearStateUI()
            }
        }

        CircularLoading(isLoading = loading)

        if (loading.not()) {
            val selected = players.toList().filter {
                it.isChecked
            }.size
            RowPlayerInNewMatchHeader(
                selected = selected,
                scrollState = scrollState
            )

            if (hasMinNumberPlayers) {

                Spacer(Modifier.height(6.dp))

                ButtonRounded {
                    viewModel.saveMatch(
                        players.filter {
                            it.isChecked
                        }
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                itemsIndexed(players) { index, item ->
                    RowPlayerInNewMatch(
                        player = item,
                        isChecked = item.isChecked,
                        scrollState = scrollState
                    ) { isChecked ->
                        item.isChecked = isChecked
                        if (index in players.indices) {
                            val itemToRemove = players.removeAt(index)
                            if (isChecked) {
                                players.add(0, itemToRemove)
                            } else {
                                players.add(itemToRemove)
                            }
                        }

                        hasMinNumberPlayers = players.count { it.isChecked } >= 6
                    }
                }
            }
        }
    }
}
