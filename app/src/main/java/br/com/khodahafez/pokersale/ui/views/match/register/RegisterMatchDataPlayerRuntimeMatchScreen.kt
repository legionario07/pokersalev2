package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.model.RegisterMatchDataRuntimeModel
import br.com.khodahafez.pokersale.ui.components.RowPlayerDataRuntimeHeader
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatchHeader
import br.com.khodahafez.pokersale.ui.components.RowPlayerInRuntimeMatch
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchViewModelFactory

@Composable
fun RegisterMatchDataPlayerRuntimeMatchScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterMatchViewModel = viewModel(
        factory = RegisterMatchViewModelFactory(
            context = LocalContext.current
        )
    ),
    onSaveMatchSuccessful: () -> Unit
) {

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

        val players = remember {
            mutableStateListOf<RegisterMatchDataRuntimeModel>()
        }

        var totalValue by remember {
            mutableDoubleStateOf(0.0)
        }

        var fivePercentage by remember {
            mutableDoubleStateOf(0.0)
        }

        LaunchedEffect(
            totalValue
        ) {
            fivePercentage = viewModel.calculatePercentage(totalValue)
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
                if (loading) {
                    loading = false
                    players.addAll(result.players.map {
                        RegisterMatchDataRuntimeModel(
                            player = it
                        )
                    })
                }
            }

            is RegisterMatchStateUI.SaveSuccessful -> {
                viewModel.clearStateUI()
            }
        }

        CircularLoading(isLoading = loading)

        if (loading.not()) {
            val selected = players.toList().filter {
                it.isChecked
            }.size
            RowPlayerDataRuntimeHeader(
                selected = selected,
                scrollState = scrollState
            )

            Spacer(Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        bottom = 8.dp
                    )
            ) {
                itemsIndexed(players) { index, item ->
                    RowPlayerInRuntimeMatch(
                        viewModel = viewModel,
                        player = item,
                        isChecked = item.isChecked,
                        scrollState = scrollState,
                        onChangedValue = {
                            totalValue = viewModel.calculateExpense(players)
                        }
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

                        totalValue = viewModel.calculateExpense(players)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 4.dp
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: $totalValue | 5% = $fivePercentage [Premiação: ${
                        totalValue - (fivePercentage * 2)
                    }]",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
