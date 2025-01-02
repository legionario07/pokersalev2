package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.PokerSaleConstants.Domain.MIN_PLAYERS_FOR_MATCH
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.FillDataUserDialog
import br.com.khodahafez.pokersale.ui.views.components.SingleSelectDialog
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchViewModelFactory

@Composable
fun RegisterMatchScreen(
    viewModel: RegisterMatchViewModel = viewModel(
        factory = RegisterMatchViewModelFactory(
            context = LocalContext.current
        )
    ),
    idMatchCreated: String,
    onSaveMatchSuccessful: () -> Unit
) {

    val context = LocalContext.current

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.getMatchById(idMatchCreated)
        }
    }

    val players = remember {
        mutableStateListOf<RegisterMatchScreenModel>()
    }

    val playersWithExpanse = remember {
        mutableStateListOf<RegisterMatchScreenModel>()
    }

    val playerClicked = remember {
        mutableStateOf(Player())
    }

    val isShowDialogAddPlayer = remember {
        mutableStateOf(false)
    }

    val isShowDialogRemovePlayer = remember {
        mutableStateOf(false)
    }

    val isShowDialogExpense = remember {
        mutableStateOf(false)
    }

    val isShowButtonSave = remember {
        derivedStateOf { playersWithExpanse.size >= MIN_PLAYERS_FOR_MATCH }
    }

    var loading by remember {
        mutableStateOf(false)
    }

    val uiState by viewModel.stateUI.collectAsState()

    when (val result = uiState) {
        is RegisterMatchStateUI.GetPlayerRuntimeState,
        is RegisterMatchStateUI.InitialState -> {
            // Do Nothing
        }

        is RegisterMatchStateUI.Loading -> {
            loading = true
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
            onSaveMatchSuccessful()
            viewModel.clearStateUI()
        }
    }

    if (playersWithExpanse.isEmpty()) {
        DisclaimerScreen()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        PlayersListContent(players = playersWithExpanse) { player ->
            playerClicked.value = player
            isShowDialogExpense.value = true
        }
        Spacer(modifier = Modifier.height(mediumDimens.size16))
        RegisterMatchContentScreen(
            isShowButtonSave = isShowButtonSave,
            onClickSave = {
                viewModel.saveMatch(playersWithExpanse)
            },
            onClickRemove = {
                isShowDialogRemovePlayer.value = true
            }
        ) {
            isShowDialogAddPlayer.value = true
        }
    }

    if (isShowDialogAddPlayer.value) {
        SingleSelectDialog(
            optionsList = players.toList().minusByFor(playersWithExpanse.toList()) {
                it.player.id
            },
            onSubmitButtonClick = { playerSelected ->
                playersWithExpanse.add(RegisterMatchScreenModel(playerSelected))
                isShowDialogAddPlayer.value = false
            },
            onDismissRequest = {
                isShowDialogAddPlayer.value = false
            }
        )
    }

    if (isShowDialogRemovePlayer.value) {
        SingleSelectDialog(
            optionsList = playersWithExpanse,
            onSubmitButtonClick = { playerRemoved ->
                playersWithExpanse.removeIf {
                    it.player == playerRemoved
                }
                isShowDialogRemovePlayer.value = false
            },
            onDismissRequest = {
                isShowDialogRemovePlayer.value = false
            }
        )
    }

    if (isShowDialogExpense.value) {
        FillDataUserDialog(
            playerWithExpense = playersWithExpanse.first {
                it.player.id == playerClicked.value.id
            },
            onDismissRequest = {
                isShowDialogExpense.value = false
            }
        ) { registerMatchDataUserScreenModel ->
            playersWithExpanse.removeIf {
                it.player.id == playerClicked.value.id
            }
            playersWithExpanse.add(
                RegisterMatchScreenModel(
                    player = playerClicked.value,
                    totalEntries = viewModel.calculateExpense(
                        registerMatchDataUserScreenModel
                    ),
                    buy = registerMatchDataUserScreenModel.buyInCounter,
                    reBuy = registerMatchDataUserScreenModel.reBuyCounter,
                    doubleReBuy = registerMatchDataUserScreenModel.doubleReBuyCounter,
                    addon = registerMatchDataUserScreenModel.addonCounter,
                    prize = registerMatchDataUserScreenModel.prize,
                    bounties = registerMatchDataUserScreenModel.bountyCounter,
                    position = registerMatchDataUserScreenModel.position
                )
            )
        }
    }

}

@Composable
private fun DisclaimerScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = mediumDimens.size42,
                start = mediumDimens.size24,
                end = mediumDimens.size24
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.poker_sale_disclaimer),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RegisterMatchContentScreen(
    isShowButtonSave: State<Boolean>,
    onClickSave: () -> Unit,
    onClickRemove: () -> Unit,
    onClickAdd: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = mediumDimens.size24
            ),
        verticalArrangement = Arrangement.Bottom
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(mediumDimens.size12)
        ) {

            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    onClickRemove()
                },
                colors = buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = RoundedCornerShape(mediumDimens.size05),
            ) {
                Text(stringResource(id = R.string.poker_sale_match_register_remove_player))
            }

            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    onClickAdd()
                },
                colors = buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = RoundedCornerShape(mediumDimens.size05),
            ) {
                Text(stringResource(id = R.string.poker_sale_match_register_add_player))
            }
        }

        if (isShowButtonSave.value) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onClickSave()
                },
                shape = RoundedCornerShape(mediumDimens.size05),
            ) {
                Text(stringResource(id = R.string.poker_sale_match_register_label))
            }
        }
    }
}

@Composable
private fun PlayersListContent(
    players: List<RegisterMatchScreenModel>,
    onClickCard: (Player) -> Unit
) {


    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.8f)
    ) {
        items(players) { registerMatchScreenModel ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(mediumDimens.size65)
                    .padding(mediumDimens.size06)
                    .clickable(onClick = { onClickCard(registerMatchScreenModel.player) }),
                shape = RoundedCornerShape(mediumDimens.size06),
                elevation = CardDefaults.cardElevation(mediumDimens.size04)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(start = mediumDimens.size30),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = registerMatchScreenModel.player.name,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Text(
                        text = registerMatchScreenModel.totalEntries.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

data class RegisterMatchScreenModel(
    val player: Player,
    val buy: Int = 1,
    var reBuy: Int = 0,
    var doubleReBuy: Int = 0,
    var addon: Int = 1,
    var tax: Int = 0,
    var bounties: Int = 0,
    var totalEntries: Double = 0.0,
    var prize: Double = 0.0,
    var position: Int = 10,
    var isChecked: Boolean = false
)

fun <T, R> Collection<T>.minusByFor(elements: Collection<T>, selector: (T) -> R?) =
    filter { t -> elements.none { selector(it) == selector(t) } }