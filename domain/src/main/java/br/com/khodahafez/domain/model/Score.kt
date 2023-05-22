package br.com.khodahafez.domain.model

data class Score(
    val id: Int,
    val players: Player? = null,
    val bounties: List<Bounty> = mutableListOf(),
    val positionInTheMatch: PositionInTheMatch? = null,
    val participationScore: Int,
    val difficultyScore: Int
)
