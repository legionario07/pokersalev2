package br.com.khodahafez.domain.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.khodahafez.domain.model.database.PlayerDb

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getAll(): List<PlayerDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg playerDb: PlayerDb)
}
