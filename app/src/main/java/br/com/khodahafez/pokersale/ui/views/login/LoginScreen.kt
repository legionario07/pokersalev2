package br.com.khodahafez.pokersale.ui.views.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.R
import br.com.khodahafez.pokersale.ui.components.YearSelector
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading
import br.com.khodahafez.pokersale.ui.views.components.TextFieldComponent

@Composable
internal fun LoginScreen(
    context: Context,
    viewModel: LoginViewModel =
        LoginViewModelFactory.create(
            context = context
        )!!,
    onLoginSuccessful: (Player) -> Unit
) {

    val loginStateUI by viewModel.loginStateUI.collectAsState()

    var loading by remember {
        mutableStateOf(false)
    }

    var isEnabledButton by remember {
        mutableStateOf(false)
    }

    when (val result = loginStateUI) {
        is LoginStateUI.InitialState -> {
            // Do Noting
        }

        is LoginStateUI.LoginSuccessful -> {
            onLoginSuccessful(result.player)
            viewModel.clearStateUI()
        }

        is LoginStateUI.Loading -> {
            loading = true
        }

        is LoginStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }

        is LoginStateUI.LoginByPreferencesState -> {
            loading = false
            viewModel.clearStateUI()
        }
    }

    CircularLoading(isLoading = loading)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Version()

        Spacer(Modifier.weight(1f))

        LoginScreenContent(
            loginValue = viewModel.login,
            passwordValue = viewModel.password,
        ) { login, password ->
            viewModel.login = login
            viewModel.password = password
            isEnabledButton = login.isNotEmpty() && password.isNotEmpty()
        }

        LabelChecked(
            isCheckedValue = viewModel.isCheckedToRemember
        ) { isChecked ->
            viewModel.isCheckedToRemember = isChecked
        }

        YearSelector { year ->
            Session.yearSelected = year
        }

        Spacer(Modifier.weight(1f))

        ButtonLogin(
            isEnabledButton = isEnabledButton
        ) {
            viewModel.login(
                login = viewModel.login,
                password = viewModel.password
            )
        }
    }
}

@Composable
private fun LoginScreenContent(
    loginValue: String = EMPTY_STRING,
    passwordValue: String = EMPTY_STRING,
    onChangedValue: (String, String) -> Unit,
) {

    var login by remember { mutableStateOf(loginValue) }
    var password by remember { mutableStateOf(passwordValue) }

    LaunchedEffect(login, password) {
        onChangedValue(login, password)
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = mediumDimens.size24)
    ) {
        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.Person,
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
        Spacer(modifier = Modifier.height(mediumDimens.size10))
    }
}

@Composable
fun Version() {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(mediumDimens.size24),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Versão: 1.1.0",
            fontSize = 10.sp
        )
    }
}

@Composable
fun ButtonLogin(
    modifier: Modifier = Modifier,
    isEnabledButton: Boolean,
    onClickButton: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                all = mediumDimens.size24
            ),
        onClick = {
            onClickButton()
        },
        enabled = isEnabledButton,
        shape = RoundedCornerShape(mediumDimens.size05),
    ) {
        Text(stringResource(id = R.string.poker_sale_button_label))
    }
}

@Composable
private fun LabelChecked(
    isCheckedValue: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {

    var isChecked by remember {
        mutableStateOf(isCheckedValue)
    }

    LabeledCheckBox(
        label = stringResource(
            id = R.string.poker_sale_remember_label
        ),
        onCheckChanged = {
            isChecked = isChecked.not()
            onCheckedChanged(isChecked)
        },
        isChecked = isChecked
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current
    LoginScreen(
        context = context
    ) { }
}
