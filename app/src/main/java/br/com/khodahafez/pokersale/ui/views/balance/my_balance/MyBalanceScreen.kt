package br.com.khodahafez.pokersale.ui.views.balance.my_balance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.model.ExpenseUiHelper
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading

@Composable
fun MyBalanceScreen(
    viewModel: MyBalanceViewModel = viewModel(factory = MyBalanceViewModelFactory()),
) {

    val stateUI by viewModel.stateUI.collectAsState()
    val context = LocalContext.current

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is MyBalanceStateUI.Initial -> {
            // Do Noting
        }

        is MyBalanceStateUI.GetSuccessfulBalanceStateUI -> {
            loading = false
            MyBalanceContentScreen(result.expenseUiHelper)
        }

        is MyBalanceStateUI.Loading -> {
            loading = true
        }

        is MyBalanceStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    CircularLoading(isLoading = loading)
}

@Composable
private fun MyBalanceContentScreen(
    expenseUiHelper: ExpenseUiHelper
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = expenseUiHelper.totalEntries.toString())
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = expenseUiHelper.totalPrizes.toString())
    }
}
