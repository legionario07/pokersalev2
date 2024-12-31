package br.com.khodahafez.pokersale.ui.views.match.register.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.PokerSaleApplication
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryDataSourceProvide
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
        val playerDao = PokerSaleApplication.database?.playerDao()
        val repositoryExpenses = RepositoryModule.provideExpensesRepository(dbReferencesExpenses)
        val repositoryScores = RepositoryModule.provideScoreRepository(dbReferencesScores)

        val mapper = MapperProvide.providePlayerMapper()
        val scoreMapper = MapperProvide.provideScoreMapper()

        val repositoryDataSource = RepositoryDataSourceProvide.providePlayerRepositoryDataSource(
            mapper = mapper,
            playerRepository = repositoryPlayer,
            playerDao = playerDao!!,
            session = Session
        )

        val getAllPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(
            repositoryDataSource = repositoryDataSource
        )

        val updateMatchUseCase = UseCaseModule.provideUpdateMatchUseCase(repositoryMatch)
        val getMatchUseCase = UseCaseModule.provideGetMatchUseCase(repositoryMatch)
        val saveExpensesUseCase = UseCaseModule.provideSaveExpensesUseCase(repositoryExpenses)
        val saveScoreUseCase = UseCaseModule.provideSaveScoreUseCase(
            repositoryScores,
            scoreMapper
        )

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
