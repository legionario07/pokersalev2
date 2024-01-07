package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.usecase.match.register.SaveMatchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RegisterMatchViewModel(
    private val playerLogged: Player?,
    private val saveUseCase: SaveMatchUseCase,
) : ViewModel() {

    private val _stateUI = MutableStateFlow<RegisterMatchStateUI>(RegisterMatchStateUI.InitialState)
    val stateUI: StateFlow<RegisterMatchStateUI> = _stateUI

    fun save(matchOfPoker: MatchOfPoker) {
        matchOfPoker.registeredBy = playerLogged
        viewModelScope.launch {
            saveUseCase.save(
                matchOfPoker
            ).catch {
                _stateUI.emit(
                    RegisterMatchStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
                )
            }.collect {
                _stateUI.emit(RegisterMatchStateUI.RegisterMatchSuccessful)
            }
        }
    }
}

sealed class RegisterMatchStateUI {
    object InitialState : RegisterMatchStateUI()
    data class Error(val message: String?) : RegisterMatchStateUI()
    object RegisterMatchSuccessful : RegisterMatchStateUI()
}
