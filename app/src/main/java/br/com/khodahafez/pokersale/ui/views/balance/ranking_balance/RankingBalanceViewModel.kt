package br.com.khodahafez.pokersale.ui.views.balance.ranking_balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.dto.ExpensesDto
import br.com.khodahafez.domain.model.screen.RankingBalanceUiHelper
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.expenses.GetAllExpensesUseCase
import br.com.khodahafez.domain.usecase.player.GetAllPlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RankingBalanceViewModel(
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val getAllPlayerUseCase: GetAllPlayerUseCase
) : ViewModel() {

    private val _stateUI =
        MutableStateFlow<RankingBalanceStateUI>(RankingBalanceStateUI.Initial)
    val stateUI: StateFlow<RankingBalanceStateUI> = _stateUI

    private val expenses: MutableList<ExpensesDto> = mutableListOf()
    private val players: MutableList<Player> = mutableListOf()

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            getAllExpensesUseCase.getAll()
                .catch { error ->
                    _stateUI.update {
                        RankingBalanceStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            expenses.addAll(resultOf.response)
                            getAllPlayers()
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RankingBalanceStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                RankingBalanceStateUI.Loading
                            }
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
                        RankingBalanceStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            _stateUI.update {
                                RankingBalanceStateUI.GetAllBalances(mutableListOf())
                            }
                            players.addAll(resultOf.response)
                            _stateUI.update {
                                RankingBalanceStateUI.GetAllBalances(
                                    getTransformToRankingBalanceHelperUI()
                                )
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RankingBalanceStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                RankingBalanceStateUI.Loading
                            }
                        }
                    }
                }
        }
    }

    private fun getTransformToRankingBalanceHelperUI(): List<RankingBalanceUiHelper> {
        return players.map { player ->
            player to expenses.filter { expense ->
                player.id == expense.idPlayer
            }
        }.filter {
            it.second.isNotEmpty()
        }.map {
            var totalEntries = 0.0
            var totalPrize = 0.0
            it.second.forEach { expense ->
                totalEntries += expense.totalEntries ?: 0.0
                totalPrize += expense.cashPrize ?: 0.0
            }
            RankingBalanceUiHelper(
                player = it.first,
                totalEntries = totalEntries,
                totalPrizes = totalPrize,
                profit = totalPrize - totalEntries
            )
        }.sortedBy {
            it.profit
        }.reversed()
    }

    fun clearState() {
        _stateUI.update {
            RankingBalanceStateUI.Initial
        }
    }
}

sealed class RankingBalanceStateUI {
    object Initial : RankingBalanceStateUI()
    object Loading : RankingBalanceStateUI()
    data class Error(val message: String?) : RankingBalanceStateUI()
    data class GetAllBalances(val expenses: List<RankingBalanceUiHelper>) : RankingBalanceStateUI()
}
