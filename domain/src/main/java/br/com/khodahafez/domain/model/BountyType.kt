package br.com.khodahafez.domain.model

enum class BountyType(val points: Int) {

    NORMAL(2),
    ESPECIAL(4);
    companion object{
        fun getBountyByMatchType(isSpecialMatch: Boolean): BountyType {
            if(isSpecialMatch) {
                return ESPECIAL
            }
            return NORMAL
        }
    }
}
