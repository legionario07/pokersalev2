package br.com.khodahafez.pokersale.ui.views.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.components.TextFieldComponent
import br.com.khodahafez.pokersale.ui.views.login.LabeledCheckBox
import br.com.khodahafez.pokersale.ui.views.login.PasswordField

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel(factory = PlayerViewModelFactory()),

    ) {

    val loginStateUI by viewModel.playerStateUI.collectAsState()
    val context = LocalContext.current

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = loginStateUI) {
        is PlayerStateUI.InitialState -> {
            // Do Noting
        }

        is PlayerStateUI.SaveSuccessful -> {
            loading = false
            context.showToast("Salvo com Sucesso")
        }

        is PlayerStateUI.Loading -> {
            loading = true
        }

        is PlayerStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    PlayerScreenContent { name, login, password, isAdmin ->
        viewModel.save(
            name = name,
            login = login,
            password = password,
            isAdmin = isAdmin
        )
    }

    CircularLoading(isLoading = loading)
}

@Composable
private fun PlayerScreenContent(
    onClick: (String, String, String, Boolean) -> Unit
) {

    var name by remember { mutableStateOf(EMPTY_STRING) }
    var login by remember { mutableStateOf(EMPTY_STRING) }
    var password by remember { mutableStateOf(EMPTY_STRING) }
    var isAdmin by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {

        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.Edit,
            value = name,
            label = "Nome",
            placeholder = "Digite o nome",
            onChange = {
                name = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.Edit,
            value = login,
            label = "Login",
            placeholder = "Digite o login",
            onChange = {
                login = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        PasswordField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onChange = {
                password = it
            },
            submit = { },
        )
        Spacer(modifier = Modifier.height(24.dp))
        LabeledCheckBox(
            label = stringResource(
                id = R.string.poker_sale_register_player_is_admin
            ),
            onCheckChanged = {
                isAdmin = isAdmin.not()
            },
            isChecked = isAdmin
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
                onClick(
                    name,
                    login,
                    password,
                    isAdmin
                )
            },
            enabled = login.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty(),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_button_label))
        }
    }
}