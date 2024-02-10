package br.com.khodahafez.pokersale.ui.views.home

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.PokerSaleApplication.Companion.database
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryDataSourceProvide
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class HomeViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferencesScore = FirebaseModule.provideFirebaseReference("/scores")
        val dbReferencesPlayers = FirebaseModule.provideFirebaseReference("/users")
        val dbReferencesMatches = FirebaseModule.provideFirebaseReference("/matches")

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferencesPlayers)
        val scoreRepository = RepositoryModule.provideScoreRepository(dbReferencesScore)
        val playerDao = database?.playerDao()
        val scoreDao = database?.scoreDao()

        val mapper = MapperProvide.providePlayerMapper()
        val scoreMapper = MapperProvide.provideScoreMapper()

        val repositoryDataSource = RepositoryDataSourceProvide.providePlayerRepositoryDataSource(
            mapper = mapper,
            playerRepository = playerRepository,
            playerDao = playerDao!!,
            session = Session
        )

        val repositoryMatches = RepositoryModule.provideMatchOfPokerRepository(dbReferencesMatches)

        val repositoryScoreDataSource =
            RepositoryDataSourceProvide.provideScoreRepositoryDataSource(
                mapper = scoreMapper,
                scoreRepository = scoreRepository,
                scoreDao = scoreDao!!,
                session = Session
            )

        val getPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(
            repositoryDataSource = repositoryDataSource
        )
        val getScoreUseCase = UseCaseModule.provideGetAllScoreUseCase(
            repositoryDataSource = repositoryScoreDataSource
        )

        val getMatchUseCase = UseCaseModule.provideGetMatchUseCase(
            repository = repositoryMatches
        )

        return HomeViewModel(
            getAllScoreUseCase = getScoreUseCase,
            getAllPlayerUseCase = getPlayerUseCase,
            getMatchUseCase = getMatchUseCase
        ) as T
    }
}
