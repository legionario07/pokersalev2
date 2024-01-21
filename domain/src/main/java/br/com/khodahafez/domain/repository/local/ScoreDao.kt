package br.com.khodahafez.domain.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.khodahafez.domain.model.database.ScoreDb

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores")
    fun getAll(): List<ScoreDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg scoreDb: ScoreDb)
}
