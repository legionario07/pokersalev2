package br.com.khodahafez.pokersale.ui.views.match.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Beenhere
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.views.components.IconButtonComponent
import br.com.khodahafez.pokersale.ui.views.components.TextFieldComponent
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchDataUserViewModelFactory


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

    val bountyCounter = remember {
        mutableStateOf(0)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val listItems = mutableListOf(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9
    )

    val disabledItem = 1
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

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_bounties),
            value = bountyCounter.value.toString(),
            onClickRemove = { counter ->
                bountyCounter.value = counter.toInt().dec()
            },
            onClickAdd = { counter ->
                bountyCounter.value = counter.toInt().inc()
            }
        )

        val context = LocalContext.current

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 24.dp,
                    top = 4.dp,
                    bottom = 4.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "Posição",
                textAlign = TextAlign.Center ,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    // adding items
                    listItems.forEachIndexed { itemIndex, itemValue ->
                        DropdownMenuItem(
                            onClick = {
                                Toast.makeText(context, itemValue.toString(), Toast.LENGTH_SHORT)
                                    .show()
                                expanded = false
                            },
                            text = {
                                Text(text = itemValue.toString())
                            },
                            enabled = (itemIndex != disabledItem),
                        )
                    }
                }
            }
        }

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 4.dp
                ),
            icon = Icons.Filled.MonetizationOn,
            value = "0.0",
            label = stringResource(id = R.string.poker_sale_match_prize),
            keyboardType = KeyboardType.Number,
            onChange = {
                println(it)
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
                4.dp
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
