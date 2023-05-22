package br.com.khodahafez.domain.model

data class PositionInTheMatch(
    val id: Int,
    val hashMap: List<PositionScore> = mutableListOf()
)

data class PositionScore(
    val id: Int,
    val position: Int,
    val points: Int,
)
