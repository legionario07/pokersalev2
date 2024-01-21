package br.com.khodahafez.pokersale.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.khodahafez.domain.model.database.BalanceDb
import br.com.khodahafez.domain.model.database.ExpensesDb
import br.com.khodahafez.domain.model.database.PlayerDb
import br.com.khodahafez.domain.model.database.ScoreDb
import br.com.khodahafez.domain.repository.local.BalanceDao
import br.com.khodahafez.domain.repository.local.ExpensesDao
import br.com.khodahafez.domain.repository.local.PlayerDao
import br.com.khodahafez.domain.repository.local.ScoreDao


@Database(
    entities = [PlayerDb::class, ScoreDb::class, ExpensesDb::class, BalanceDb::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao
    abstract fun expensesDao(): ExpensesDao
    abstract fun balanceDao(): BalanceDao
}

fun provideDataBase(applicationContext: Context) =
    Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "poker-sale-v2-db"
    ).build()
