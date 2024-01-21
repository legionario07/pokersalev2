package br.com.khodahafez.pokersale.ui.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataUserContentScreen
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataUserScreenModel
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchScreenModel

@Composable
fun SingleSelectDialog(
    optionsList: List<RegisterMatchScreenModel>,
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
                            item.player,
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
fun RadioButtonItem(
    player: Player,
    selectedValue: RegisterMatchScreenModel?,
    onClickListener: (Player) -> Unit
) {
    Row(Modifier
        .fillMaxWidth()
        .selectable(
            selected = (player.name == selectedValue?.player?.name.orEmpty()),
            onClick = {
                onClickListener(player)
            }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (player.name == selectedValue?.player?.name.orEmpty()),
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
    playerWithExpense: RegisterMatchScreenModel,
    onDismissRequest: () -> Unit,
    onClickSave: (RegisterMatchDataUserScreenModel) -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier.fillMaxHeight(0.83f),
            shape = RoundedCornerShape(10.dp)
        ) {
            RegisterMatchDataUserContentScreen(
                registerMatchScreenModel = playerWithExpense,
            ) {
                onDismissRequest()
                onClickSave(it)
            }
        }
    }
}