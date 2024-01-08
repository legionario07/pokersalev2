package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.components.TextFieldComponent
import br.com.khodahafez.pokersale.ui.views.login.LabeledCheckBox

@Composable
fun RegisterMatchDataEntryScreen(
    viewModel: RegisterMatchViewModel = viewModel(factory = RegisterMatchViewModelFactory()),
    onContinueFlow: (String) -> Unit
) {
    val stateUI by viewModel.stateUI.collectAsState()
    val context = LocalContext.current
    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is RegisterMatchStateUI.InitialState -> {
            // Do Noting
        }

        is RegisterMatchStateUI.SaveSuccessful -> {
            onContinueFlow(result.idMatchCreated)
        }

        is RegisterMatchStateUI.Loading -> {
           loading = true
        }

        is RegisterMatchStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    RegisterMatchScreenContent(viewModel) {
        viewModel.save()
    }

    CircularLoading(isLoading = loading)
}

@Composable
fun RegisterMatchScreenContent(
    viewModel: RegisterMatchViewModel,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
    ) {

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                ),
            icon = Icons.Filled.MonetizationOn,
            value = viewModel.buyIn,
            label = stringResource(id = R.string.poker_sale_match_buy_in),
            keyboardType = KeyboardType.Number,
            onChange = {
                viewModel.buyIn = it
            }
        )

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                ),
            icon = Icons.Filled.MonetizationOn,
            value = viewModel.simpleReBuy,
            label = stringResource(id = R.string.poker_sale_match_simple_re_buy),
            keyboardType = KeyboardType.Number,
            onChange = {
                viewModel.simpleReBuy = it
            }
        )

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                ),
            icon = Icons.Filled.MonetizationOn,
            value = viewModel.doubleReBuy,
            label = stringResource(id = R.string.poker_sale_match_double_re_buy),
            keyboardType = KeyboardType.Number,
            onChange = {
                viewModel.doubleReBuy = it
            }
        )

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                ),
            icon = Icons.Filled.MonetizationOn,
            value = viewModel.addon,
            label = stringResource(id = R.string.poker_sale_match_addon),
            keyboardType = KeyboardType.Number,
            onChange = {
                viewModel.addon = it
            }
        )

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                ),
            icon = Icons.Filled.MonetizationOn,
            value = viewModel.tax,
            label = stringResource(id = R.string.poker_sale_match_tax),
            keyboardType = KeyboardType.Number,
            onChange = {
                viewModel.tax = it
            }
        )

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                ),
            icon = Icons.Filled.Numbers,
            value = viewModel.ranking,
            label = stringResource(id = R.string.poker_sale_match_ranking_number),
            placeholder = stringResource(id = R.string.poker_sale_match_ranking_number_label) ,
            keyboardType = KeyboardType.Number,
            onChange = {
                viewModel.ranking = it
            }
        )

        LabeledCheckBox(
            label = stringResource(
                id = R.string.poker_sale_match_is_special_match
            ),
            onCheckChanged = {
                viewModel.isSpecialMatch = !viewModel.isSpecialMatch
            },
            isChecked = viewModel.isSpecialMatch
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 24.dp
            )
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onClick()
            },
            enabled = true, // TODO
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_match_register_button_label))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp
                ),
            onClick = {
                // TODO
            },
            enabled = true, // TODO
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_match_register_cancel_button_label))
        }
    }
}
