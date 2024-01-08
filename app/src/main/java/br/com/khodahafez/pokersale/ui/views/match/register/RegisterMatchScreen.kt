package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.views.match.register.factory.RegisterMatchViewModelFactory

@Composable
fun RegisterMatchScreen(
    viewModel: RegisterMatchDataEntryViewModel = viewModel(factory = RegisterMatchViewModelFactory()),
    idMatchCreated: String,
) {

    Text(
        modifier= Modifier.fillMaxSize(),
        text = idMatchCreated
    )
}
