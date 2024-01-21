package br.com.khodahafez.data.mapper

import br.com.khodahafez.domain.mapper.ScoreMapper
import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.model.database.ScoreDb
import br.com.khodahafez.domain.model.dto.ScoreDto

class ScoreMapperImpl : ScoreMapper {

    override fun toDomain(scoreDb: ScoreDb): Score {
        return Score(
            id = scoreDb.id,
            idPlayer = scoreDb.idPlayer,
            idMatchOfPlayer = scoreDb.idMatchOfPlayer,
            totalBounties = scoreDb.totalBounties,
            bountiesPoints = scoreDb.bountiesPoints,
            positionInTheMatch = scoreDb.positionInTheMatch,
            pointsForPosition = scoreDb.pointsForPosition,
            difficultyScore = scoreDb.difficultyScore,
            pointsForParticipation = scoreDb.pointsForParticipation
        )
    }

    override fun toDomain(scoreDto: ScoreDto): Score {
        return Score(
            id = scoreDto.id,
            idPlayer = scoreDto.idPlayer,
            idMatchOfPlayer = scoreDto.idMatchOfPlayer,
            totalBounties = scoreDto.totalBounties,
            bountiesPoints = scoreDto.bountiesPoints,
            positionInTheMatch = scoreDto.positionInTheMatch,
            pointsForPosition = scoreDto.pointsForPosition,
            difficultyScore = scoreDto.difficultyScore,
            pointsForParticipation = scoreDto.pointsForParticipation
        )
    }

    override fun toEntityDto(score: Score): ScoreDto {
        return ScoreDto(
            id = score.id,
            idPlayer = score.idPlayer,
            idMatchOfPlayer = score.idMatchOfPlayer,
            totalBounties = score.totalBounties,
            bountiesPoints = score.bountiesPoints,
            positionInTheMatch = score.positionInTheMatch,
            pointsForPosition = score.pointsForPosition,
            difficultyScore = score.difficultyScore,
            pointsForParticipation = score.pointsForParticipation
        )
    }

    override fun toEntityDb(score: Score): ScoreDb {
        return ScoreDb(
            id = score.id.orEmpty(),
            idPlayer = score.idPlayer.orEmpty(),
            idMatchOfPlayer = score.idMatchOfPlayer.orEmpty(),
            totalBounties = score.totalBounties,
            bountiesPoints = score.bountiesPoints,
            positionInTheMatch = score.positionInTheMatch,
            pointsForPosition = score.pointsForPosition,
            difficultyScore = score.difficultyScore,
            pointsForParticipation = score.pointsForParticipation
        )
    }
}
