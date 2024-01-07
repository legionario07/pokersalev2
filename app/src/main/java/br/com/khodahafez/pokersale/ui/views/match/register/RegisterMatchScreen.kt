package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterMatchScreen(
    viewModel: RegisterMatchViewModel = viewModel(factory = RegisterMatchViewModelFactory()),
) {
    val stateUI by viewModel.stateUI.collectAsState()
}