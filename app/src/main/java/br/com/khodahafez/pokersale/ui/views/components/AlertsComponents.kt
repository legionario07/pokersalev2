package br.com.khodahafez.pokersale.ui.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataUserContentScreen

@Composable
fun ItemAlertDialog(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    currentInventory: Int,
    itemDescription: String,
    itemNumber: String,
) {
    var incoming by remember { mutableStateOf(0f) }
    var outgoing by remember { mutableStateOf(0f) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = itemNumber,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = itemDescription,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentInventory.toString(),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Magenta,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                // Update Inventory
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {

                        Text(text = "Incoming ${incoming.toInt()}")
                        Slider(
                            value = incoming,
                            onValueChange = { incoming = it },
                            valueRange = 0f..100f,
                            onValueChangeFinished = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Outgoing ${outgoing.toInt()}")
                        Slider(
                            value = outgoing,
                            onValueChange = { outgoing = it },
                            valueRange = 0f..100f,
                            onValueChangeFinished = {}
                        )
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onNegativeClick) {
                        Text(text = "CANCEL")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onPositiveClick.invoke()
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
fun SingleSelectDialog(
    optionsList: List<Player>,
    onSubmitButtonClick: (Player) -> Unit,
    onDismissRequest: () -> Unit,
) {

    val selectedIndex = remember {
        mutableStateOf(-1)
    }

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier.fillMaxHeight(0.8f),
            shape = RoundedCornerShape(10.dp)
        ) {

            Column(modifier = Modifier.padding(10.dp)) {

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn {
                    items(optionsList) { item ->
                        RadioButtonItem(
                            item,
                            optionsList.getOrNull(selectedIndex.value)
                        ) { selectedValue ->
                            onSubmitButtonClick(selectedValue)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButtonItem(player: Player, selectedValue: Player?, onClickListener: (Player) -> Unit) {
    Row(Modifier
        .fillMaxWidth()
        .selectable(
            selected = (player.name == selectedValue?.name.orEmpty()),
            onClick = {
                onClickListener(player)
            }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (player.name == selectedValue?.name.orEmpty()),
            onClick = {
                onClickListener(player)
            }
        )
        Text(
            text = player.name,
            style = MaterialTheme.typography.bodySmall.merge(),
            modifier = Modifier
        )
    }
}


@Composable
fun FillDataUserDialog(
    player: Player,
    onDismissRequest: () -> Unit,
    onClickSave: (Expenses) -> Unit
) {

    val expense = remember {
        mutableStateOf(Expenses())
    }


    Dialog(onDismissRequest = { onDismissRequest()}) {
        Surface(
            modifier = Modifier.fillMaxHeight(0.7f),
            shape = RoundedCornerShape(10.dp)
        ) {
            RegisterMatchDataUserContentScreen(
                player = player,
                expense = expense
            ) {
                onDismissRequest()
                onClickSave(it)
            }
        }
    }
}