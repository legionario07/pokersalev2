package br.com.khodahafez.pokersale.ui.views.match.register.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchViewModel

class RegisterMatchViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/matches")
        val dbReferencesPlayers = FirebaseModule.provideFirebaseReference("/users")
        val dbReferencesExpenses = FirebaseModule.provideFirebaseReference("/expenses")
        val dbReferencesScores = FirebaseModule.provideFirebaseReference("/scores")

        val repositoryMatch = RepositoryModule.provideMatchOfPokerRepository(dbReferences)
        val repositoryPlayer = RepositoryModule.providePlayerRepository(dbReferencesPlayers)
        val repositoryExpenses = RepositoryModule.provideExpensesRepository(dbReferencesExpenses)
        val repositoryScores = RepositoryModule.provideScoreRepository(dbReferencesScores)

        val updateMatchUseCase = UseCaseModule.provideUpdateMatchUseCase(repositoryMatch)
        val getAllPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(repositoryPlayer)
        val getMatchUseCase = UseCaseModule.provideGetMatchUseCase(repositoryMatch)
        val saveExpensesUseCase = UseCaseModule.provideSaveExpensesUseCase(repositoryExpenses)
        val saveScoreUseCase = UseCaseModule.provideSaveScoreUseCase(repositoryScores)

        return RegisterMatchViewModel(
            playerLogged = Session.player,
            getMatchUseCase = getMatchUseCase,
            getAllPlayerUseCase = getAllPlayerUseCase,
            saveExpensesUseCase = saveExpensesUseCase,
            saveScoreUseCase = saveScoreUseCase,
            updateMatchUseCase = updateMatchUseCase
        ) as T
    }
}
