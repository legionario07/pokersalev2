package br.com.khodahafez.pokersale.ui.views.balance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.model.BankType
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.components.TextFieldComponent

@Composable
fun BalanceScreen(
    viewModel: BalanceViewModel = viewModel(factory = BalanceViewModelFactory()),
    onSuccessFinish: () -> Unit
) {
    val stateUI by viewModel.stateUI.collectAsState()
    val context = LocalContext.current

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is BalanceStateUI.Initial -> {
            // Do Noting
        }

        is BalanceStateUI.SaveSuccessfulBalanceStateUI -> {
            onSuccessFinish()
            loading = false
            viewModel.clearState()
        }

        is BalanceStateUI.Loading -> {
            loading = true
        }

        is BalanceStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    BalanceContentScreen { bankType, value, reason ->
        viewModel.save(
            bankType = bankType,
            value = value,
            reason = reason
        )
    }

    CircularLoading(isLoading = loading)
}

@Composable
private fun BalanceContentScreen(
    onClickButton: (BankType, String, String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var bankType by remember {
        mutableStateOf(BankType.CASH_IN)
    }

    var value by remember { mutableStateOf("0.0") }
    var reason by remember { mutableStateOf(EMPTY_STRING) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.MonetizationOn,
            keyboardType = KeyboardType.Number,
            label = stringResource(id = R.string.poker_sale_balance_value),
            value = value,
            onChange = {
                value = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.Note,
            placeholder = stringResource(id = R.string.poker_sale_balance_enter_the_reason),
            label = stringResource(id = R.string.poker_sale_balance_reason),
            value = reason,
            onChange = {
                reason = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .align(Alignment.CenterHorizontally)
                .padding(
                    all = 24.dp
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    },
                text = bankType.name,
                textAlign = TextAlign.Center
            )

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        all = 24.dp
                    ),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                BankType.values().forEachIndexed { _, itemValue ->
                    DropdownMenuItem(
                        onClick = {
                            bankType = itemValue
                            expanded = false
                        },
                        text = {
                            Text(text = itemValue.toString())
                        },
                        enabled = (itemValue != bankType),
                    )
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 24.dp
                ),
            onClick = {
                onClickButton(
                    bankType, value, reason
                )
            },
            enabled = value != "0.0" && reason.isNotEmpty(),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_balance_update_balance))
        }
    }

}
