package com.schluterman.golftracker.data.local

import com.schluterman.golftracker.domain.GolfRoundEntity
import kotlinx.coroutines.flow.Flow

class GolfRoundRepository(private val golfRoundDao: GolfRoundDao) {

    val golfRounds: Flow<List<GolfRoundEntity>> = golfRoundDao.getAllRounds()

    // suspend fun getRoundById(id: Int): GolfRoundEntity? = golfRoundDao.getRoundById(id)

    suspend fun insertRound(round: GolfRoundEntity) {
        golfRoundDao.insertRound(round)
    }

    suspend fun updateRound(round: GolfRoundEntity) {
        golfRoundDao.updateRound(round)
    }

    suspend fun deleteRound(round: GolfRoundEntity) {
        golfRoundDao.deleteRound(round)
    }
}