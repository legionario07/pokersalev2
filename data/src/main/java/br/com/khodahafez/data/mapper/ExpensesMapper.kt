package br.com.khodahafez.data.mapper

import br.com.khodahafez.domain.mapper.PokerSaleMapper
import br.com.khodahafez.domain.model.dto.ExpensesDto
import br.com.khodahafez.domain.model.screen.RankingBalanceUiHelper

class ExpensesMapper : PokerSaleMapper<ExpensesDto, RankingBalanceUiHelper> {

    override fun toDomain(entity: ExpensesDto): RankingBalanceUiHelper {
        return RankingBalanceUiHelper()
    }
}
