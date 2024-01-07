package br.com.khodahafez.domain.model

data class Score(
    val id: String? = null,
    val players: Player? = null,
    val matchOfPokerType: MatchOfPokerType,
    val totalBounties: Int = 0,
    val bountyType: BountyType,
    val positionInTheMatch: Int,
    val participationScore: PositionScore,
    val difficultyScore: Int
)
