package br.com.khodahafez.domain.mapper

interface PokerSaleMapper<Entity, Domain> {
    fun toDomain(
        entity: Entity
    ): Domain
}
