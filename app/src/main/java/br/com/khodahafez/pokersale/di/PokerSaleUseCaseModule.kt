package br.com.khodahafez.pokersale.di

import android.content.Context
import br.com.khodahafez.domain.mapper.PlayerMapper
import br.com.khodahafez.domain.mapper.ScoreMapper
import br.com.khodahafez.domain.repository.PlayerRepositoryDataSource
import br.com.khodahafez.domain.repository.ScoreRepositoryDataSource
import br.com.khodahafez.domain.repository.local.Preferences
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
import br.com.khodahafez.domain.repository.remote.ExpensesRepository
import br.com.khodahafez.domain.repository.remote.MatchRepository
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
import br.com.khodahafez.domain.repository.remote.ScoreRepository
import br.com.khodahafez.domain.usecase.balance.GetAllBalanceUseCase
import br.com.khodahafez.domain.usecase.balance.SaveBalanceUseCase
import br.com.khodahafez.domain.usecase.bounty_type.SaveBountyTypeUseCase
import br.com.khodahafez.domain.usecase.expenses.GetAllExpensesUseCase
import br.com.khodahafez.domain.usecase.expenses.GetExpenseByPlayerUseCase
import br.com.khodahafez.domain.usecase.expenses.SaveExpensesUseCase
import br.com.khodahafez.domain.usecase.login.LoginUseCase
import br.com.khodahafez.domain.usecase.match.register.GetMatchUseCase
import br.com.khodahafez.domain.usecase.match.register.SaveMatchUseCase
import br.com.khodahafez.domain.usecase.match.register.UpdateMatchUseCase
import br.com.khodahafez.domain.usecase.player.GetAllPlayerUseCase
import br.com.khodahafez.domain.usecase.player.SavePlayerUseCase
import br.com.khodahafez.domain.usecase.position_score.GetAllPositionScoreUseCase
import br.com.khodahafez.domain.usecase.position_score.SavePositionScoreUseCase
import br.com.khodahafez.domain.usecase.preferences.LoginPreferencesUseCase
import br.com.khodahafez.domain.usecase.preferences.PokerSalePreferencesUseCase
import br.com.khodahafez.domain.usecase.score.GetAllScoreUseCase
import br.com.khodahafez.domain.usecase.score.SaveScoreUseCase
import br.com.khodahafez.domain.utils.EncryptUtils
import br.com.khodahafez.domain.utils.Session
import kotlinx.coroutines.Dispatchers


object UseCaseModule {
    fun provideLoginUseCase(
        playerRepository: PlayerRepository,
        mapper: PlayerMapper
    ): LoginUseCase {
        return LoginUseCase(
            scope = Dispatchers.IO,
            playerRepository = playerRepository,
            encryptUtil = EncryptUtils,
            mapper = mapper
        )
    }

    fun providePlayerSaveUseCase(
        playerRepository: PlayerRepository,
        mapper: PlayerMapper,
    ): SavePlayerUseCase {
        return SavePlayerUseCase(
            scope = Dispatchers.IO,
            playerRepository = playerRepository,
            encryptUtil = EncryptUtils,
            mapper = mapper,
            session = Session
        )
    }

    fun provideSavePositionScoreUseCase(
        positionScoreRepository: PositionScoreRepository,
    ): SavePositionScoreUseCase {
        return SavePositionScoreUseCase(
            scope = Dispatchers.IO,
            positionScoreRepository = positionScoreRepository,
        )
    }

    fun provideGetAllPositionScoreUseCase(
        positionScoreRepository: PositionScoreRepository,
    ): GetAllPositionScoreUseCase {
        return GetAllPositionScoreUseCase(
            scope = Dispatchers.IO,
            positionScoreRepository = positionScoreRepository,
        )
    }

    fun provideSaveBountyTypeUseCase(
        repository: BountyTypeRepository,
    ): SaveBountyTypeUseCase {
        return SaveBountyTypeUseCase(
            scope = Dispatchers.IO,
            repository = repository,
        )
    }

    fun provideSaveBalanceUseCase(
        repository: BalanceRepository,
    ): SaveBalanceUseCase {
        return SaveBalanceUseCase(
            scope = Dispatchers.IO,
            repository = repository,
        )
    }

    fun provideGetAllBalanceUseCase(
        repository: BalanceRepository,
    ): GetAllBalanceUseCase {
        return GetAllBalanceUseCase(
            scope = Dispatchers.IO,
            repository = repository,
        )
    }

    fun provideGetAllExpensesUseCase(
        repository: ExpensesRepository,
    ): GetAllExpensesUseCase {
        return GetAllExpensesUseCase(
            scope = Dispatchers.IO,
            repository = repository,
        )
    }

    fun provideSaveMatchOfPokerUseCase(
        repository: MatchRepository
    ): SaveMatchUseCase {
        return SaveMatchUseCase(
            scope = Dispatchers.IO,
            repository = repository
        )
    }

    fun provideGetAllPlayerUseCase(
        repositoryDataSource: PlayerRepositoryDataSource,
    ): GetAllPlayerUseCase {
        return GetAllPlayerUseCase(
            scope = Dispatchers.IO,
            repositoryDataSource = repositoryDataSource,
        )
    }

    fun provideGetMatchUseCase(
        repository: MatchRepository
    ): GetMatchUseCase {
        return GetMatchUseCase(
            scope = Dispatchers.IO,
            repository = repository
        )
    }

    fun provideSaveExpensesUseCase(
        repository: ExpensesRepository
    ): SaveExpensesUseCase {
        return SaveExpensesUseCase(
            scope = Dispatchers.IO,
            repository = repository
        )
    }

    fun provideSaveScoreUseCase(
        repository: ScoreRepository,
        mapper: ScoreMapper
    ): SaveScoreUseCase {
        return SaveScoreUseCase(
            scope = Dispatchers.IO,
            repository = repository,
            mapper = mapper,
            session = Session
        )
    }

    fun provideGetAllScoreUseCase(
        repositoryDataSource: ScoreRepositoryDataSource
    ): GetAllScoreUseCase {
        return GetAllScoreUseCase(
            scope = Dispatchers.IO,
            repositoryDataSource = repositoryDataSource
        )
    }

    fun provideUpdateMatchUseCase(
        repository: MatchRepository
    ): UpdateMatchUseCase {
        return UpdateMatchUseCase(
            scope = Dispatchers.IO,
            repository = repository
        )
    }

    fun provideGetExpensesByPlayerUseCase(
        repository: ExpensesRepository
    ): GetExpenseByPlayerUseCase {
        return GetExpenseByPlayerUseCase(
            scope = Dispatchers.IO,
            repository = repository
        )
    }

    fun provideLoginPreferencesUseCase(
        context: Context,
        preferences: Preferences = RepositoryModule.providePreferencesRepository(
            context = context
        )
    ) = LoginPreferencesUseCase(
        preferences = preferences
    )

    fun providePokerSalePreferencesUseCase(
        context: Context,
        preferences: Preferences = RepositoryModule.providePreferencesRepository(
            context = context
        )
    ) = PokerSalePreferencesUseCase(
        preferences = preferences
    )
}