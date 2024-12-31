package br.com.khodahafez.domain.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.khodahafez.domain.model.database.ExpensesDb

@Dao
interface ExpensesDao {
    @Query("SELECT * FROM expenses")
    fun getAll(): List<ExpensesDb>

    @Query("SELECT * FROM expenses where id_player LIKE :idPlayer")
    fun getByIdPlayer(idPlayer: String): List<ExpensesDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg expensesDb: ExpensesDb)
}
