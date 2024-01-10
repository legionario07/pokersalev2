package br.com.khodahafez.domain.model

data class Score(
    var id: String? = null,
    val idPlayer: String,
    val idMatchOfPlayer: String,
    val totalBounties: Int = 0,
    var bountiesPoints: Int = 0,
    val positionInTheMatch: Int,
    var pointsForPosition: Int = 0,
    var difficultyScore: Int = 0,
    val pointsForParticipation: Int = 0,
)
