package br.com.khodahafez.data.mapper

import br.com.khodahafez.domain.mapper.PokerSaleMapper
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.model.screen.RankingBalanceUiHelper

class ExpensesMapper : PokerSaleMapper<Expenses, RankingBalanceUiHelper> {

    override fun toDomain(entity: Expenses): RankingBalanceUiHelper {
        return RankingBalanceUiHelper()
    }
}
