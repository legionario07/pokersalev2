package br.com.khodahafez.domain.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.khodahafez.domain.model.BankType

@Entity(tableName = "balances")
data class BalanceDb(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name_player") val namePlayer: String,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "operation_type") val operationType: BankType,
    @ColumnInfo(name = "reason") val reason: String,
)
