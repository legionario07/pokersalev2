package br.com.khodahafez.pokersale.ui.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.match.register.GetMatchUseCase
import br.com.khodahafez.domain.usecase.player.GetAllPlayerUseCase
import br.com.khodahafez.domain.usecase.score.GetAllScoreUseCase
import br.com.khodahafez.pokersale.ui.model.PlayerHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllScoreUseCase: GetAllScoreUseCase,
    private val getAllPlayerUseCase: GetAllPlayerUseCase,
    private val getMatchUseCase: GetMatchUseCase
) : ViewModel() {

    private val _homeStateUI = MutableStateFlow<HomeStateUI>(HomeStateUI.InitialState)
    val homeStateUI: StateFlow<HomeStateUI> = _homeStateUI

    private val scores: MutableList<Score> = mutableListOf()
    private val players: MutableSet<Player> = mutableSetOf()

    init {
        getMatchByRanking()
    }

    fun getMatchByRanking(rankingNumber: Int = 1) {
        viewModelScope.launch {
            _homeStateUI.update {
                HomeStateUI.Loading
            }
            getMatchUseCase.getByRanking(rankingNumber)
                .catch { error ->
                    _homeStateUI.update {
                        HomeStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            if (resultOf.response.isEmpty()) {
                                _homeStateUI.update {
                                    HomeStateUI.EmptyRanking
                                }
                            } else {
                                getAllScoreByRaking(resultOf.response)
                            }
                        }

                        is ResultOf.Failure -> {
                            _homeStateUI.update {
                                HomeStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _homeStateUI.update {
                                HomeStateUI.Loading
                            }
                        }
                    }
                }
        }
    }

    private fun getAllScoreByRaking(listMatches: List<MatchOfPoker>) {
        viewModelScope.launch {
            val idMatches = listMatches.map {
                it.id!!
            }
            getAllScoreUseCase.getAllByRanking(idMatches)
                .catch { error ->
                    _homeStateUI.update {
                        HomeStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            scores.clear()
                            scores.addAll(resultOf.response)
                            getAllPlayers()
                        }

                        is ResultOf.Failure -> {
                            _homeStateUI.update {
                                HomeStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _homeStateUI.update {
                                HomeStateUI.Loading
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
                    _homeStateUI.update {
                        HomeStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            players.addAll(resultOf.response)
                            _homeStateUI.update {
                                HomeStateUI.GetAllSuccessful(
                                    getTransformToPlayerHelper()
                                )
                            }
                        }

                        is ResultOf.Failure -> {
                            _homeStateUI.update {
                                HomeStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _homeStateUI.update {
                                HomeStateUI.Loading
                            }
                        }
                    }
                }
        }
    }

    private fun
            getTransformToPlayerHelper(): List<PlayerHelper> {
        return players.map { player ->
            player to scores.filter { score ->
                player.id == score.idPlayer
            }
        }.filter {
            it.second.isNotEmpty()
        }.map {
            var totalPoints = 0
            var totalBounties = 0
            it.second.forEach { score ->
                totalPoints += score.difficultyScore + score.pointsForPosition + score.pointsForParticipation + score.bountiesPoints
                totalBounties += score.totalBounties
            }
            PlayerHelper(
                player = it.first,
                pointsTotal = totalPoints,
                bounties = totalBounties
            )
        }
            .sortedWith(
                compareByDescending<PlayerHelper> { it.pointsTotal }
                    .thenByDescending { it.bounties }
                    .thenBy { it.player.name }
            )
    }
}

sealed class HomeStateUI {
    object InitialState : HomeStateUI()
    object Loading : HomeStateUI()
    data class Error(val message: String?) : HomeStateUI()

    object EmptyRanking: HomeStateUI()
    data class GetAllSuccessful(val listPlayerHelper: List<PlayerHelper>) : HomeStateUI()
}
