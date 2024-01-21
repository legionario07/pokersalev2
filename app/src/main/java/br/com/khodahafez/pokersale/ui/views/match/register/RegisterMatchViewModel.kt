package br.com.khodahafez.pokersale.ui.views.match.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.MatchOfPokerType
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.dto.ExpensesDto
import br.com.khodahafez.domain.model.dto.ScoreDto
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.expenses.SaveExpensesUseCase
import br.com.khodahafez.domain.usecase.match.register.GetMatchUseCase
import br.com.khodahafez.domain.usecase.match.register.UpdateMatchUseCase
import br.com.khodahafez.domain.usecase.player.GetAllPlayerUseCase
import br.com.khodahafez.domain.usecase.score.SaveScoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterMatchViewModel(
    private val playerLogged: Player?,
    private val updateMatchUseCase: UpdateMatchUseCase,
    private val getMatchUseCase: GetMatchUseCase,
    private val getAllPlayerUseCase: GetAllPlayerUseCase,
    private val saveExpensesUseCase: SaveExpensesUseCase,
    private val saveScoreUseCase: SaveScoreUseCase,
) : ViewModel() {

    private val _stateUI = MutableStateFlow<RegisterMatchStateUI>(RegisterMatchStateUI.InitialState)
    val stateUI: StateFlow<RegisterMatchStateUI> = _stateUI

    private var matchOfPoker: MatchOfPoker? = null

    init {
        getAllPlayers()
    }

    fun saveMatch(listRegisterMatch: List<RegisterMatchScreenModel>) {
        matchOfPoker?.registeredBy = playerLogged
        matchOfPoker?.players?.addAll(listRegisterMatch.map {
            it.player
        })
        matchOfPoker?.matchOfPokerType = MatchOfPokerType.FINISHED

        matchOfPoker?.let { match ->
            viewModelScope.launch {
                updateMatchUseCase.update(
                    match
                ).catch { error ->
                    _stateUI.update {
                        RegisterMatchStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            saveExpenses(listRegisterMatch)
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RegisterMatchStateUI.Error(resultOf.error.message)
                            }
                        }

                        is ResultOf.Loading -> {
                            _stateUI.update {
                                RegisterMatchStateUI.Loading
                            }
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            listRegisterMatch.forEach {
                saveScoreUseCase.save(
                    scoreDto = ScoreDto(
                        idPlayer = it.player.id.orEmpty(),
                        idMatchOfPlayer = matchOfPoker?.id.orEmpty(),
                        totalBounties = it.bounties,
                        positionInTheMatch = it.position
                    ),
                    matchOfPoker = matchOfPoker
                ).catch { error ->
                    _stateUI.update {
                        RegisterMatchStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            // Do Nothing
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RegisterMatchStateUI.Error(resultOf.error.message)
                            }
                        }

                        is ResultOf.Loading -> {
                            // Do Nothing
                        }
                    }
                }
            }
        }
    }

    private fun saveExpenses(listRegisterMatch: List<RegisterMatchScreenModel>) =
        viewModelScope.launch {
            listRegisterMatch.forEach { registerMatchScreenModel ->
                saveExpensesUseCase.save(
                    ExpensesDto(
                        idPlayer = registerMatchScreenModel.player.id,
                        totalEntries = registerMatchScreenModel.totalEntries,
                        cashPrize = registerMatchScreenModel.prize,
                        idMatchOfPoker = matchOfPoker?.id.orEmpty()
                    )
                ).catch { error ->
                    _stateUI.update {
                        RegisterMatchStateUI.Error(error.message)
                    }
                }.collect { resultOf ->

                    when (resultOf) {
                        is ResultOf.Success -> {
                            _stateUI.update {
                                RegisterMatchStateUI.SaveSuccessful(matchOfPoker?.id.orEmpty())
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RegisterMatchStateUI.Error(resultOf.error.message)
                            }
                        }

                        is ResultOf.Loading -> {
                            // Do Nothing
                        }
                    }
                }
            }
        }

    fun getMatchById(idMatch: String) {
        viewModelScope.launch {
            getMatchUseCase.getById(idMatch)
                .catch { error ->
                    _stateUI.update {
                        RegisterMatchStateUI.Error(error.message)
                    }
                }
                .collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            matchOfPoker = resultOf.response
                        }

                        else -> {
                            // Do Nothing
                        }
                    }
                }
        }
    }

    private fun getAllPlayers() {
        viewModelScope.launch {
            getAllPlayerUseCase.getAll()
                .catch { error ->
                    _stateUI.update {
                        RegisterMatchStateUI.Error(error.message)
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

    fun clearStateUI() {
        _stateUI.update {
            RegisterMatchStateUI.InitialState
        }
    }

    fun calculateExpense(
        registerMatchDataUserScreenModel: RegisterMatchDataUserScreenModel
    ): Double {
        var total = 0.0
        with(registerMatchDataUserScreenModel) {
            total += buyInCounter * matchOfPoker?.buyInValue!!
            total += reBuyCounter * matchOfPoker?.reBuyValue!!
            total += doubleReBuyCounter * matchOfPoker?.doubleReBuyValue!!
            total += addonCounter * matchOfPoker?.addonValue!!
            total += taxCounter * matchOfPoker?.taxValue!!
        }

        return total
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
