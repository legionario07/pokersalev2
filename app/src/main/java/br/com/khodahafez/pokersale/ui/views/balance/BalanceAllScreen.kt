package br.com.khodahafez.pokersale.ui.views.balance

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading

@Composable
fun BalanceAllScreen(
    viewModel: BalanceAllViewModel = viewModel(factory = BalanceAllViewModelFactory()),
) {
    val stateUI by viewModel.stateUI.collectAsState()
    val context = LocalContext.current

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is BalancePokerStateUI.Initial -> {
            // Do Noting
        }

        is BalancePokerStateUI.GetAllBalances -> {
            BalanceAllContentScreen(list = result.listBalances)
            loading = false
        }

        is BalancePokerStateUI.Loading -> {
            loading = true
        }

        is BalancePokerStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    CircularLoading(isLoading = loading)
}

@Composable
private fun BalanceAllContentScreen(list: List<Balance>) {
    Text(
        text = list.toString()
    )
}