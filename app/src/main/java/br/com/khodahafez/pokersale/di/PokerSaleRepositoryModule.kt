package br.com.khodahafez.pokersale.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import br.com.khodahafez.data.repository.firebase.BalanceRepositoryImpl
import br.com.khodahafez.data.repository.firebase.BountyTypeRepositoryImpl
import br.com.khodahafez.data.repository.firebase.ExpensesRepositoryImpl
import br.com.khodahafez.data.repository.firebase.MatchRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PositionScoreRepositoryImpl
import br.com.khodahafez.data.repository.firebase.ScoreRepositoryImpl
import br.com.khodahafez.data.repository.local.PreferencesRepositoryImpl
import br.com.khodahafez.domain.PokerSaleConstants.PreferencesKeys.KEY_SHARED_PREFERENCES
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
import br.com.khodahafez.domain.repository.remote.ExpensesRepository
import br.com.khodahafez.domain.repository.remote.MatchRepository
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
import br.com.khodahafez.domain.repository.remote.ScoreRepository
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson
import java.util.prefs.Preferences

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

    fun provideMatchOfPokerRepository(databaseReference: DatabaseReference): MatchRepository {
        return MatchRepositoryImpl(
            databaseReference = databaseReference
        )
    }

    fun provideExpensesRepository(databaseReference: DatabaseReference): ExpensesRepository {
        return ExpensesRepositoryImpl(
            databaseReference = databaseReference
        )
    }

    fun provideScoreRepository(databaseReference: DatabaseReference): ScoreRepository {
        return ScoreRepositoryImpl(
            databaseReference = databaseReference
        )
    }

    private fun providePreferences(
        activity: Activity,
        key: String = KEY_SHARED_PREFERENCES
    ): SharedPreferences {
        return activity.getSharedPreferences(
          key, Context.MODE_PRIVATE
        )
    }

    fun providePreferencesRepository(
        activity: Activity,
        sharedPreferences: SharedPreferences = providePreferences(activity)
    ) = PreferencesRepositoryImpl(
        sharedPreferences = sharedPreferences,
        gson = Gson()
    )
}
