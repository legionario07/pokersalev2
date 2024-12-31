package br.com.khodahafez.domain.model

enum class PositionScoreType(val position: Int, val points: Int) {
    _1(1, 25),
    _2(2, 15),
    _3(3, 10),
    _4(4, 7),
    _5(5, 5),
    _6(6, 4),
    _7(7, 3),
    _8(8, 2),
    _9(9, 1),
    OTHER(10, 0);

    companion object {
        fun getPointsForPosition(position: Int): PositionScoreType {
            return values().firstOrNull {
                it.position == position
            } ?: OTHER
        }
    }
}
