package br.com.khodahafez.domain.mapper

import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.model.database.ScoreDb
import br.com.khodahafez.domain.model.dto.ScoreDto

interface ScoreMapper {
    fun toDomain(scoreDb: ScoreDb): Score
    fun toDomain(scoreDto: ScoreDto): Score
    fun toEntityDto(score: Score): ScoreDto
    fun toEntityDb(score: Score): ScoreDb
}
