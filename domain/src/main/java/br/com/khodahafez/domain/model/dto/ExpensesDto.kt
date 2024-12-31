package br.com.khodahafez.domain.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpensesDto(
    var id: String? = null,
    val idPlayer: String? = null,
    val date: String? = null,
    val totalEntries: Double? = null,
    val cashPrize: Double? = null,
    val idMatchOfPoker: String? = null
): Parcelable
