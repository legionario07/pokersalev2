package br.com.khodahafez.domain
object PokerSaleConstants {
    const val EMPTY_STRING = ""

    object ErrorMessage {
        const val NOT_FOUND_USER = "Usuário não encontrado"
        const val PASSWORD_ERROR = "Senha incorreta"
        const val GENERIC_ERROR = "Ocorreu um erro ao processar a solicitação"
        const val DATA_NOT_FOUND_ERROR = "Erro ao procurar informação no Banco de Dados"
    }

    object MatchValues {
        const val BUY_IN = "30.00"
        const val RE_BUY = "30.00"
        const val DOUBLE_RE_BUY = "50.00"
        const val ADDON = "50.00"
        const val TAX = "0.00"

        const val MESSAGE_ERROR_RANKING_EMPTY = "Digite um valor para o ranking"
    }
}
