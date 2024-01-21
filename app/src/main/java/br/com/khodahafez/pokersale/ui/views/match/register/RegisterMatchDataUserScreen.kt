package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
import br.com.khodahafez.pokersale.ui.views.components.IconButtonComponent
import br.com.khodahafez.pokersale.ui.views.components.TextFieldComponent

@Composable
fun RegisterMatchDataUserContentScreen(
    registerMatchScreenModel: RegisterMatchScreenModel,
    onClickSave: (RegisterMatchDataUserScreenModel) -> Unit,
) {

    val reBuyCounter = remember {
        mutableStateOf(registerMatchScreenModel.reBuy)
    }

    val doubleReBuyCounter = remember {
        mutableStateOf(registerMatchScreenModel.doubleReBuy)
    }

    val addonCounter = remember {
        mutableStateOf(registerMatchScreenModel.addon)
    }

    val taxCounter = remember {
        mutableStateOf(registerMatchScreenModel.tax)
    }

    val bountyCounter = remember {
        mutableStateOf(registerMatchScreenModel.bounties)
    }

    val prize = remember {
        mutableStateOf(registerMatchScreenModel.prize)
    }

    val position = remember {
        mutableStateOf(registerMatchScreenModel.position)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val listItems = mutableListOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9
    )

    Column(
        modifier = Modifier.padding(
            top = mediumDimens.size32,
            bottom = mediumDimens.size16
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
                text = registerMatchScreenModel.player.name,
                fontSize = 24.sp,
            )
        }

        Spacer(modifier = Modifier.height(mediumDimens.size12))

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = mediumDimens.size24,
                    top = mediumDimens.size04,
                    bottom = mediumDimens.size04
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.padding(end = mediumDimens.size16),
                text = stringResource(id = R.string.poker_sale_position),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    listItems.forEachIndexed { itemIndex, itemValue ->
                        DropdownMenuItem(
                            onClick = {
                                position.value = itemValue
                                expanded = false
                            },
                            text = {
                                Text(text = itemValue.toString())
                            },
                            enabled = (itemValue != position.value),
                        )
                    }
                }
            }
        }

        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = mediumDimens.size24,
                    end = mediumDimens.size24,
                    top = mediumDimens.size04
                ),
            icon = Icons.Filled.MonetizationOn,
            value = prize.value.toString(),
            label = stringResource(id = R.string.poker_sale_match_prize),
            keyboardType = KeyboardType.Number,
            onChange = {
                prize.value = it.toDouble()
            }
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = mediumDimens.size24
                ),
            onClick = {
                onClickSave(
                    RegisterMatchDataUserScreenModel(
                        reBuyCounter = reBuyCounter.value,
                        doubleReBuyCounter = doubleReBuyCounter.value,
                        addonCounter = addonCounter.value,
                        taxCounter = taxCounter.value,
                        prize = prize.value,
                        bountyCounter = bountyCounter.value,
                        position = position.value
                    )
                )
            },
            shape = RoundedCornerShape(mediumDimens.size05),
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
            modifier = Modifier.padding(end = mediumDimens.size16),
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
                start = mediumDimens.size16
            ),
            isEnabled = isEnabled,
            imageVector = Icons.Default.Remove,
            size = mediumDimens.size24
        ) {
            onClickRemove(
                value
            )
        }
        IconButtonComponent(
            imageVector = Icons.Default.Add,
            isEnabled = isEnabled,
            size = mediumDimens.size24
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
    val prize: Double,
    val bountyCounter: Int,
    val position: Int
)
