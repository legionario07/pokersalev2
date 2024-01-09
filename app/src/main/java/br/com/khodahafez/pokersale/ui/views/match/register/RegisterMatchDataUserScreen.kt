package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.views.components.IconButtonComponent
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchDataUserViewModelFactory

@Composable
fun RegisterMatchDataUserScreen(
    viewModel: RegisterMatchDataUserViewModel = viewModel(factory = RegisterMatchDataUserViewModelFactory()),
    player: Player
) {

    val expense = remember {
        mutableStateOf(Expenses())
    }


    RegisterMatchDataUserContentScreen(
        player = player,
    ) {

    }
}

@Composable
fun RegisterMatchDataUserContentScreen(
    player: Player,
    onClickSave: (RegisterMatchDataUserScreenModel) -> Unit,
) {

    val reBuyCounter = remember {
        mutableStateOf(0)
    }

    val doubleReBuyCounter = remember {
        mutableStateOf(0)
    }

    val addonCounter = remember {
        mutableStateOf(0)
    }

    val taxCounter = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.padding(
            top = 32.dp,
            bottom = 16.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = player.name,
                fontSize = 24.sp,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_simple_re_buy),
            value = reBuyCounter.value.toString(),
            onClickRemove = { counter ->
                reBuyCounter.value = counter.toInt().dec()
            },
            onClickAdd = { counter ->
                reBuyCounter.value = counter.toInt().inc()
            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_double_re_buy),
            value = doubleReBuyCounter.value.toString(),
            onClickRemove = { counter ->
                doubleReBuyCounter.value = counter.toInt().dec()
            },
            onClickAdd = { counter ->
                doubleReBuyCounter.value = counter.toInt().inc()
            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_addon),
            value = addonCounter.value.toString(),
            isEnabled = addonCounter.value < 1,
            onClickRemove = { counter ->
                addonCounter.value = counter.toInt().dec()
            },
            onClickAdd = { counter ->
                addonCounter.value = counter.toInt().inc()
            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_tax),
            value = taxCounter.value.toString(),
            isEnabled = taxCounter.value < 1,
            onClickRemove = { counter ->
                taxCounter.value = counter.toInt().dec()
            },
            onClickAdd = { counter ->
                taxCounter.value = counter.toInt().inc()
            }
        )
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
                onClickSave(
                    RegisterMatchDataUserScreenModel(
                        reBuyCounter = reBuyCounter.value,
                        doubleReBuyCounter = doubleReBuyCounter.value,
                        addonCounter = addonCounter.value,
                        taxCounter = taxCounter.value
                    )
                )
            },
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_match_register_save_label))
        }
    }

}

@Composable
private fun RowEntryValue(
    label: String,
    value: String,
    isEnabled: Boolean = true,
    onClickRemove: (String) -> Unit,
    onClickAdd: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .then(
                if (isEnabled.not()) {
                    Modifier.background(Color.LightGray.copy(alpha = 0.5f))
                } else Modifier
            )
            .fillMaxWidth()
            .padding(
                8.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        IconButtonComponent(
            modifier = Modifier.padding(
                start = 16.dp
            ),
            isEnabled = isEnabled,
            imageVector = Icons.Default.Remove,
            size = 24.dp
        ) {
            onClickRemove(
                value
            )
        }
        IconButtonComponent(
            imageVector = Icons.Default.Add,
            isEnabled = isEnabled,
            size = 24.dp
        ) {
            onClickAdd(
                value
            )
        }
    }
}

data class RegisterMatchDataUserScreenModel(
    val buyInCounter: Int = 1,
    val reBuyCounter: Int,
    val doubleReBuyCounter: Int,
    val addonCounter: Int,
    val taxCounter: Int,
)
