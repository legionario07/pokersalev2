package br.com.khodahafez.pokersale.ui.views.balance.ranking_balance

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

class RankingBalanceViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dbReferences = FirebaseModule.provideFirebaseReference("/expenses")
        val dbReferencesPlayers = FirebaseModule.provideFirebaseReference("/users")

        val repository = RepositoryModule.provideExpensesRepository(dbReferences)
        val playerDao = PokerSaleApplication.database?.playerDao()

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferencesPlayers)

        val useCase =
            UseCaseModule.provideGetAllExpensesUseCase(repository)

        val mapper = MapperProvide.providePlayerMapper()

        val repositoryDataSource = RepositoryDataSourceProvide.providePlayerRepositoryDataSource(
            mapper = mapper,
            playerRepository = playerRepository,
            playerDao = playerDao!!,
            session = Session
        )

        val getPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(
            repositoryDataSource = repositoryDataSource
        )

        return RankingBalanceViewModel(
            getAllExpensesUseCase = useCase,
            getAllPlayerUseCase = getPlayerUseCase
        ) as T
    }
}
