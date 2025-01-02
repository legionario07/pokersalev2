package br.com.khodahafez.domain.model

data class RegisterMatchDataRuntimeModel(
    val player: Player,
    var isChecked: Boolean = false,
    val buyInCounter: Int = 1,
    var reBuyCounter: Int = 0,
    var doubleReBuyCounter: Int = 0,
    var addonCounter: Int = 0,
    var taxCounter: Int = 0,
    var prize: Double = 0.0,
    var bountyCounter: Int = 0,
    var position: Int = 10,
    var expensesValue: Double = 0.0
)
