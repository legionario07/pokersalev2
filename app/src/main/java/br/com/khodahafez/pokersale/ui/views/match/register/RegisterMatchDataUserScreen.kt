package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.views.components.IconButton

@Composable
fun RegisterMatchDataUserScreen(
    viewModel: RegisterMatchDataUserViewModel = viewModel(factory = RegisterMatchDataUserModelFactory()),
){

    RegisterMatchDataUserContentScreen()
}

@Composable
private fun RegisterMatchDataUserContentScreen(){
    IconButton(imageVector = Icons.Default.Add) {
        
    }
}