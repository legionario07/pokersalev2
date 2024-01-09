package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.match.register.SaveMatchUseCase
import br.com.khodahafez.domain.usecase.player.GetAllPlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterMatchViewModel(
    private val playerLogged: Player?,
    private val saveUseCase: SaveMatchUseCase,
    private val getAllPlayerUseCase: GetAllPlayerUseCase
) : ViewModel() {

    private val _stateUI = MutableStateFlow<RegisterMatchStateUI>(RegisterMatchStateUI.InitialState)
    val stateUI: StateFlow<RegisterMatchStateUI> = _stateUI

    fun getAllPlayers() {
        viewModelScope.launch {
            getAllPlayerUseCase.getAll()
                .catch {
                    _stateUI.update {
                        RegisterMatchStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            _stateUI.update {
                                RegisterMatchStateUI.GetAllUsersState(resultOf.response)
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RegisterMatchStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                RegisterMatchStateUI.Loading
                            }
                        }
                    }
                }
        }
    }
}

sealed class RegisterMatchStateUI {
    object InitialState : RegisterMatchStateUI()
    object Loading : RegisterMatchStateUI()
    data class Error(val message: String?) : RegisterMatchStateUI()
    data class GetAllUsersState(val players: List<Player>) : RegisterMatchStateUI()
    data class SaveSuccessful(
        val idMatchCreated: String
    ) : RegisterMatchStateUI()
}
