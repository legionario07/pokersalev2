package br.com.khodahafez.domain.model

data class PositionInTheMatch(
    val id: String? = null,
    val hashMap: List<PositionScore> = mutableListOf()
)

data class PositionScore(
    var id: String? = null,
    val position: Int,
    val points: Int,
)
