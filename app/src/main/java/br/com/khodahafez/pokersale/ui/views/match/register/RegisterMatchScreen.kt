package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.FillDataUserDialog
import br.com.khodahafez.pokersale.ui.views.components.SingleSelectDialog
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchViewModelFactory

@Composable
fun RegisterMatchScreen(
    viewModel: RegisterMatchViewModel = viewModel(factory = RegisterMatchViewModelFactory()),
    idMatchCreated: String,
//    onClickInCardPlayer: (Player) -> Unit,
) {

    val players = remember {
        mutableStateListOf<Player>()
    }

    val playerClicked = remember {
        mutableStateOf<Player>(Player())
    }

    val playersSelected = remember {
        mutableStateListOf<Player>()
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

    val context = LocalContext.current
    var loading by remember {
        mutableStateOf(false)
    }

    val uiState by viewModel.stateUI.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllPlayers()
    }

    when (val result = uiState) {
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
            players.addAll(result.players)
        }

        is RegisterMatchStateUI.SaveSuccessful -> {

        }
    }

    RegisterMatchContentScreen(
        onClickRemove = { isShowDialogRemovePlayer.value = true }
    ) {
        isShowDialogAddPlayer.value = true
    }

    if (isShowDialogAddPlayer.value) {
        SingleSelectDialog(
            optionsList = players.minus(playersSelected),
            onSubmitButtonClick = { playerSelected ->
                playersSelected.add(playerSelected)
                isShowDialogAddPlayer.value = false
            },
            onDismissRequest = {
                isShowDialogAddPlayer.value = false
            }
        )
    }

    if (isShowDialogRemovePlayer.value) {
        SingleSelectDialog(
            optionsList = playersSelected,
            onSubmitButtonClick = { playerRemoved ->
                playersSelected.remove(playerRemoved)
                isShowDialogRemovePlayer.value = false
            },
            onDismissRequest = {
                isShowDialogRemovePlayer.value = false
            }
        )
    }

    if (isShowDialogExpense.value) {
        FillDataUserDialog(
            player = playerClicked.value,
            onDismissRequest = {
                isShowDialogExpense.value = false
            }
        ) {
            println("Sai")
        }
    }

    PlayersListContent(players = playersSelected) { player ->
        playerClicked.value = player
        isShowDialogExpense.value = true
    }
}

@Composable
fun RegisterMatchContentScreen(
    onClickRemove: () -> Unit,
    onClickAdd: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 24.dp
            ),
        verticalArrangement = Arrangement.Bottom
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 12.dp
                ),
            onClick = {
                onClickRemove()
            },
            colors = buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_match_register_remove_player))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onClickAdd()
            },
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_match_register_add_player))
        }
    }
}

@Composable
private fun PlayersListContent(
    players: List<Player>,
    onClickCard: (Player) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 24.dp
            ),
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
            items(players) { player ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(6.dp)
                        .clickable(onClick = { onClickCard(player) }),
                    shape = RoundedCornerShape(6.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
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
                                .padding(start = 30.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = player.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = player.id.orEmpty(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }

}
