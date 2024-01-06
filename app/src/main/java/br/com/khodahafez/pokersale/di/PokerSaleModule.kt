package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants.REFERENCIES.DATABASE_POKER_SALE
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.usecase.login.LoginUseCase
import br.com.khodahafez.domain.usecase.player.PlayerSaveUseCase
import br.com.khodahafez.domain.utils.EncryptUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object FirebaseModule {
    fun provideFirebaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(DATABASE_POKER_SALE)
    }
}

object RepositoryModule {
    fun providePlayerRepository(databaseReference: DatabaseReference): PlayerRepository {
        return PlayerRepositoryImpl(
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
}


