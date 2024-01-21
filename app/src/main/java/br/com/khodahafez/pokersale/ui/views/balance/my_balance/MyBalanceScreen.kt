package br.com.khodahafez.pokersale.ui.views.balance.my_balance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.extensions.toMonetary
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.domain.model.screen.ExpenseUiHelper
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
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
            MyBalanceEmpty()
        }
    }

    CircularLoading(isLoading = loading)
}

@Composable
private fun MyBalanceEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.poker_sale_expense_no_data), 
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun MyBalanceContentScreen(
    expenseUiHelper: ExpenseUiHelper
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        RowContent(
            label = stringResource(id = R.string.poker_sale_my_balance_entries),
            value = expenseUiHelper.totalEntries.toMonetary(),
            color = Color(0xFF993333)
        )
        RowContent(
            label = stringResource(id = R.string.poker_sale_my_balance_prizes),
            value = expenseUiHelper.totalPrizes.toMonetary(),
            color = Color(0xFF66CC66)
        )
    }
}

@Composable
private fun RowContent(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = mediumDimens.size06,
                end = mediumDimens.size06,
                bottom = mediumDimens.size06,
                top = mediumDimens.size30
            ),
        colors = CardDefaults.cardColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(mediumDimens.size06),
        elevation = CardDefaults.cardElevation(mediumDimens.size04)
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    all = mediumDimens.size12
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(mediumDimens.size10))
            Text(
                text = value,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
