package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.lifecycle.ViewModel
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.usecase.match.register.SaveMatchUseCase

class RegisterMatchDataUserViewModel(
    private val playerLogged: Player?,
    private val saveUseCase: SaveMatchUseCase,
): ViewModel() {

}
