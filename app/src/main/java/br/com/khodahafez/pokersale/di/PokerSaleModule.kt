package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.repository.firebase.BalanceRepositoryImpl
import br.com.khodahafez.data.repository.firebase.BountyTypeRepositoryImpl
import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants.REFERENCIES.DATABASE_POKER_SALE
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PositionScoreRepositoryImpl
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
import br.com.khodahafez.domain.usecase.bounty_type.SaveBountyTypeUseCase
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



