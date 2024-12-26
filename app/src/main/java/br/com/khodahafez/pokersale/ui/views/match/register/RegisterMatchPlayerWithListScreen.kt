package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatchHeaderPreview
import br.com.khodahafez.pokersale.ui.components.RowPlayerInNewMatchPreview

@Composable
fun RegisterMatchPlayerWithListScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        RowPlayerInNewMatchHeaderPreview()
        RowPlayerInNewMatchPreview()
    }
}
