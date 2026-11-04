package com.schluterman.golftracker.ui.homescreen

data class ActiveRound(
    val id: Int = 0,
    val courseName: String,
    val date: String,
    val scoreToPar: Int,
    val holesCompleted: Int
)

data class PlayerStats(
    val averageScore: Double? = null,
    val handicap: Double? = null,
    val fairwayPercentage: Double? = null
)

data class RoundSummary(
    val id: Int = 0,
    val courseName: String,
    val date: String,
    val score: Int,
    val scoreToPar: Int
)

data class HomeScreenUiState(
    val activeRound: ActiveRound? = null,
    val stats: PlayerStats? = null,
    val recentRounds: List<RoundSummary> = emptyList(),
    val isLoading: Boolean = false
)