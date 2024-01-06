package br.com.khodahafez.pokersale.ui.views.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.utils.showToast

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory()),
    onLoginSuccessful: (Player) -> Unit
) {

    val loginStateUI by viewModel.loginStateUI.collectAsState()
    val context = LocalContext.current

    when (val result = loginStateUI) {
        is LoginStateUI.InitialState -> {
            // Do Noting
        }

        is LoginStateUI.LoginSuccessful -> {
            onLoginSuccessful(result.player)
        }

        is LoginStateUI.Error -> {
            context.showToast(result.message)
        }
    }

    LoginScreenContent { login, password ->
        viewModel.login(
            login = login,
            password = password
        )
    }
}

@Composable
private fun LoginScreenContent(
    onClickButton: (String, String) -> Unit
) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        LoginField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            onChange = {
                login = it
            }
        )
        PasswordField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onChange = {
                password = it
            },
            submit = { },
        )
        Spacer(modifier = Modifier.height(10.dp))
//        LabeledCheckBox(
//            label = stringResource(
//                id = R.string.poker_sale_remember_label
//            ),
//            onCheckChanged = {
//                isChecked = isChecked.not()
//            },
//            isChecked = isChecked
//        )
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
                    login,
                    password
                )
            },
            enabled = login.isNotEmpty() && password.isNotEmpty(),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(stringResource(id = R.string.poker_sale_button_label))
        }
    }
}
