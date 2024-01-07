package br.com.khodahafez.pokersale.ui.views.position_score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.model.PositionScore
import br.com.khodahafez.domain.usecase.position_score.GetAllPositionScoreUseCase
import br.com.khodahafez.domain.usecase.position_score.SavePositionScoreUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PositionScoreViewModel(
    private val savePositionScoreUseCase: SavePositionScoreUseCase,
    private val getAllPositionScoreUseCase: GetAllPositionScoreUseCase
) : ViewModel() {

    fun save(
        position: Int,
        points: Int
    ) {

        viewModelScope.launch {
            savePositionScoreUseCase.save(
                PositionScore(
                    position = position,
                    points = points
                )
            ).catch {
                println(it.message)
            }.collect {
                println(it)
            }
        }
    }
}
