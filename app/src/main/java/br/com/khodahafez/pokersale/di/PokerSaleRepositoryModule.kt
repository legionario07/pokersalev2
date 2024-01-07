package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.repository.firebase.BalanceRepositoryImpl
import br.com.khodahafez.data.repository.firebase.BountyTypeRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PositionScoreRepositoryImpl
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
import com.google.firebase.database.DatabaseReference


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

    fun provideBountyTypeRepository(databaseReference: DatabaseReference): BountyTypeRepository {
        return BountyTypeRepositoryImpl(
            databaseReference = databaseReference
        )
    }
    fun provideBalanceRepository(databaseReference: DatabaseReference): BalanceRepository {
        return BalanceRepositoryImpl(
            databaseReference = databaseReference
        )
    }
}
