package br.com.khodahafez.domain.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreDb(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "id_player") val idPlayer: String,
    @ColumnInfo(name = "id_match_of_player") val idMatchOfPlayer: String,
    @ColumnInfo(name = "total_bounties") val totalBounties: Int,
    @ColumnInfo(name = "bounties_points") val bountiesPoints: Int,
    @ColumnInfo(name = "position_in_the_match") val positionInTheMatch: Int,
    @ColumnInfo(name = "points_for_position") val pointsForPosition: Int,
    @ColumnInfo(name = "difficulty_score") val difficultyScore: Int,
    @ColumnInfo(name = "points_for_participation") val pointsForParticipation: Int,
)
