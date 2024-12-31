package br.com.khodahafez.domain.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpensesDb(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "id_player") val idPlayer: String,
    @ColumnInfo(name = "total_entries") val totalEntries: Double,
    @ColumnInfo(name = "cash_prize") val cashPrize: Double,
    @ColumnInfo(name = "id_match_of_poker") val idMatchOfPoker: String,
)
