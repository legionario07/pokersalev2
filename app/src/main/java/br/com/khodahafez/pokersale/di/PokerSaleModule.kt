package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants.REFERENCIES.DATABASE_POKER_SALE
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PositionScoreRepositoryImpl
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
import br.com.khodahafez.domain.usecase.login.LoginUseCase
import br.com.khodahafez.domain.usecase.player.PlayerSaveUseCase
import br.com.khodahafez.domain.usecase.position_score.GetAllPositionScoreUseCase
import br.com.khodahafez.domain.usecase.position_score.SavePositionScoreUseCase
import br.com.khodahafez.domain.utils.EncryptUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers

object FirebaseModule {
    fun provideFirebaseReference(
        key: String
    ): DatabaseReference {
        val firebase = FirebaseDatabase.getInstance()
        return firebase.getReference(DATABASE_POKER_SALE.plus(key))
    }
}

object RepositoryModule {
    fun providePlayerRepository(databaseReference: DatabaseReference): PlayerRepository {
        return PlayerRepositoryImpl(
            databaseReference = databaseReference
        )
    }

    fun providePositionScoreRepository(databaseReference: DatabaseReference): PositionScoreRepository {
        return PositionScoreRepositoryImpl(
            databaseReference = databaseReference
        )
    }
}

object UseCaseModule {
    fun provideLoginUseCase(
        playerRepository: PlayerRepository,
    ): LoginUseCase {
        return LoginUseCase(
            scope = Dispatchers.IO,
            playerRepository = playerRepository,
            encryptUtil = EncryptUtils
        )
    }

    fun providePlayerSaveUseCase(
        playerRepository: PlayerRepository,
    ): PlayerSaveUseCase {
        return PlayerSaveUseCase(
            scope = Dispatchers.IO,
            playerRepository = playerRepository,
            encryptUtil = EncryptUtils
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
}


