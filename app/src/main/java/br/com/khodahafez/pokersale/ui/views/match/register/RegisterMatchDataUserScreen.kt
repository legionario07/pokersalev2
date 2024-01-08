package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.views.components.IconButton
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchDataUserViewModelFactory

@Composable
fun RegisterMatchDataUserScreen(
    viewModel: RegisterMatchDataUserViewModel = viewModel(factory = RegisterMatchDataUserViewModelFactory()),
) {

    RegisterMatchDataUserContentScreen(
        viewModel = viewModel
    )
}

@Composable
private fun RegisterMatchDataUserContentScreen(
    viewModel: RegisterMatchDataUserViewModel
) {
    Column(
        modifier = Modifier.padding(
            top = 32.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = 16.dp
        )
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Jogador A",
                fontSize = 24.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_buy_in),
            value = "0",
            isEnabled = false,
            onClickRemove = { counter ->

            },
            onClickAdd = { counter ->

            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_simple_re_buy),
            value = "0",
            onClickRemove = { counter ->

            },
            onClickAdd = { counter ->

            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_double_re_buy),
            value = "0",
            onClickRemove = { counter ->

            },
            onClickAdd = { counter ->

            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_addon),
            value = "0",
            onClickRemove = { counter ->

            },
            onClickAdd = { counter ->

            }
        )

        RowEntryValue(
            label = stringResource(id = R.string.poker_sale_match_tax),
            value = "0",
            onClickRemove = { counter ->

            },
            onClickAdd = { counter ->

            }
        )
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
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 48.dp),
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        IconButton(
            modifier = Modifier.padding(
                start = 32.dp
            ),
            isEnabled = isEnabled,
            imageVector = Icons.Default.Remove,
            size = 24.dp
        ) {}
        IconButton(
            imageVector = Icons.Default.Add,
            isEnabled = isEnabled,
            size = 24.dp
        ) {}
    }
}