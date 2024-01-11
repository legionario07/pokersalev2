package br.com.khodahafez.pokersale.ui.views.home

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class HomeViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferencesScore = FirebaseModule.provideFirebaseReference("/scores")
        val dbReferencesPlayers = FirebaseModule.provideFirebaseReference("/users")

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferencesPlayers)
        val scoreRepository = RepositoryModule.provideScoreRepository(dbReferencesScore)

        val getPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(playerRepository)
        val getScoreUseCase = UseCaseModule.provideGetScoreUseCase(scoreRepository)

        return HomeViewModel(
            getScoreUseCase = getScoreUseCase,
            getAllPlayerUseCase = getPlayerUseCase
        ) as T
    }
}
