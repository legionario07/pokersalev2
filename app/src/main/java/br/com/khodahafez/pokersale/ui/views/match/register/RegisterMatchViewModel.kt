package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.ADDON
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.BUY_IN
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.DOUBLE_RE_BUY
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.MESSAGE_ERROR_RANKING_EMPTY
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.RE_BUY
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.TAX
import br.com.khodahafez.domain.extensions.converterToStringDate
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.match.register.SaveMatchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class RegisterMatchViewModel(
    private val playerLogged: Player?,
    private val saveUseCase: SaveMatchUseCase,
) : ViewModel() {

    private val _stateUI = MutableStateFlow<RegisterMatchStateUI>(RegisterMatchStateUI.InitialState)
    val stateUI: StateFlow<RegisterMatchStateUI> = _stateUI

    fun save() {
//
//        if(messageValidations != EMPTY_STRING)  {
//            _stateUI.update {
//                RegisterMatchStateUI.Error(messageValidations)
//            }
//        }else {

//
//
//            viewModelScope.launch {
//                saveUseCase.save(
//                    matchOfPoker
//                ).catch {
//                    _stateUI.update {
//                        RegisterMatchStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
//                    }
//                }.collect { resultOf ->
//                    when (resultOf) {
//                        is ResultOf.Success -> {
//                            _stateUI.update {
//                                RegisterMatchStateUI.SaveSuccessful(resultOf.response.id.orEmpty())
//                            }
//                        }
//
//                        is ResultOf.Failure -> {
//                            _stateUI.update {
//                                RegisterMatchStateUI.Error(resultOf.error.message)
//                            }
//                        }
//
//                        else -> {
//                            _stateUI.update {
//                                RegisterMatchStateUI.Loading
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}

sealed class RegisterMatchStateUI {
    object InitialState : RegisterMatchStateUI()
    object Loading : RegisterMatchStateUI()
    data class Error(val message: String?) : RegisterMatchStateUI()
    data class SaveSuccessful(
        val idMatchCreated: String
    ) : RegisterMatchStateUI()
}
