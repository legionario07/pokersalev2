package br.com.khodahafez.pokersale.ui.views.position_score

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class PositionScoreViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/positions_scores")

        val positionScoreRepository = RepositoryModule.providePositionScoreRepository(dbReferences)

        val savePositionScoreUseCase =
            UseCaseModule.provideSavePositionScoreUseCase(positionScoreRepository)
        val getAllPositionScoreUseCase =
            UseCaseModule.provideGetAllPositionScoreUseCase(positionScoreRepository)

        return PositionScoreViewModel(
            savePositionScoreUseCase = savePositionScoreUseCase,
            getAllPositionScoreUseCase = getAllPositionScoreUseCase
        ) as T
    }
}
