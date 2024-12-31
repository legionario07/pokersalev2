package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.dto.PlayerDto
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

interface MatchRepository {
    fun save(
        matchOfPoker: MatchOfPoker
    ): Flow<ResultOf<MatchOfPoker>>

    fun update(
        matchOfPoker: MatchOfPoker
    ): Flow<ResultOf<MatchOfPoker>>


    fun getByPlayer(
        playerDto: PlayerDto,
    ): Flow<ResultOf<List<MatchOfPoker>>>

    fun getByRankingNumber(
        rankingNumber: Int,
    ): Flow<ResultOf<List<MatchOfPoker>>>

    fun getById(
        id: String,
    ): Flow<ResultOf<MatchOfPoker>>

    fun getAll(): Flow<ResultOf<List<MatchOfPoker>>>
}
