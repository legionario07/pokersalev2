package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterMatchScreen(
    idMatchCreated: String,
) {

    Text(
        modifier= Modifier.fillMaxSize(),
        text = idMatchCreated
    )
}