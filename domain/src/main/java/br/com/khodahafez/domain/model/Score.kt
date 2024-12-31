package br.com.khodahafez.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    var id: String? = null,
    val idPlayer: String? = null,
    val idMatchOfPlayer: String? = null,
    val totalBounties: Int = 0,
    var bountiesPoints: Int = 0,
    val positionInTheMatch: Int = 10,
    var pointsForPosition: Int = 0,
    var difficultyScore: Int = 0,
    val pointsForParticipation: Int = 0,
): Parcelable
