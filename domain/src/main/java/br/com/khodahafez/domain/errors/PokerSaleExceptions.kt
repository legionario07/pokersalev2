package br.com.khodahafez.domain.errors

import br.com.khodahafez.domain.PokerSaleConstants.ErrorMessage.DATA_NOT_FOUND_ERROR
import br.com.khodahafez.domain.PokerSaleConstants.ErrorMessage.NOT_FOUND_USER
import br.com.khodahafez.domain.PokerSaleConstants.ErrorMessage.PASSWORD_ERROR

class NotFoundUserException: Exception() {
    override val message: String?
        get() = NOT_FOUND_USER
}

class PasswordUserException: Exception() {
    override val message: String?
        get() = PASSWORD_ERROR
}

class NotFoundEntityException: Exception() {
    override val message: String?
        get() = DATA_NOT_FOUND_ERROR
}
