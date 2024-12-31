package br.com.khodahafez.domain.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.khodahafez.domain.model.database.BalanceDb

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balances")
    fun getAll(): List<BalanceDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg balanceDb: BalanceDb)
}
