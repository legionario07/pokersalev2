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

class RegisterMatchDataEntryViewModel(
    private val playerLogged: Player?,
    private val saveUseCase: SaveMatchUseCase,
) : ViewModel() {

    private val _stateUI = MutableStateFlow<RegisterMatchDataEntryStateUI>(RegisterMatchDataEntryStateUI.InitialDataEntryState)
    val stateUI: StateFlow<RegisterMatchDataEntryStateUI> = _stateUI

    var ranking by mutableStateOf("")
    var buyIn by mutableStateOf(BUY_IN)
    var simpleReBuy by mutableStateOf(RE_BUY)
    var doubleReBuy by mutableStateOf(DOUBLE_RE_BUY)
    var addon by mutableStateOf(ADDON)
    var tax by mutableStateOf(TAX)
    var isSpecialMatch by mutableStateOf(false)

    fun save() {

        val messageValidations = validateData()
        if(messageValidations != EMPTY_STRING)  {
            _stateUI.update {
                RegisterMatchDataEntryStateUI.Error(messageValidations)
            }
        }else {

            val matchOfPoker = MatchOfPoker(
                registeredBy = playerLogged,
                ranking = ranking.toInt(),
                date = Calendar.getInstance().timeInMillis.converterToStringDate(),
                buyInValue = buyIn.toDouble(),
                doubleReBuyValue = doubleReBuy.toDouble(),
                reBuyValue = simpleReBuy.toDouble(),
                addonValue = addon.toDouble(),
                taxValue = tax.toDouble(),
                isSpecialMatch = isSpecialMatch
            )

            viewModelScope.launch {
                saveUseCase.save(
                    matchOfPoker
                ).catch {
                    _stateUI.update {
                        RegisterMatchDataEntryStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            _stateUI.update {
                                RegisterMatchDataEntryStateUI.SaveSuccessful(resultOf.response.id.orEmpty())
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RegisterMatchDataEntryStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                RegisterMatchDataEntryStateUI.Loading
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateData(): String {
        if(ranking.isEmpty() || ranking.isBlank()) {
           return MESSAGE_ERROR_RANKING_EMPTY
        }

        return EMPTY_STRING
    }
}

sealed class RegisterMatchDataEntryStateUI {
    object InitialDataEntryState : RegisterMatchDataEntryStateUI()
    object Loading : RegisterMatchDataEntryStateUI()
    data class Error(val message: String?) : RegisterMatchDataEntryStateUI()
    data class SaveSuccessful(
        val idMatchCreated: String
    ) : RegisterMatchDataEntryStateUI()
}
