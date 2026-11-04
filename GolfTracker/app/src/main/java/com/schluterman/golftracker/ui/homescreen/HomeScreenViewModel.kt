package com.schluterman.golftracker.ui.homescreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.schluterman.golftracker.data.local.GolfRoundRepository
import com.schluterman.golftracker.domain.GolfRoundEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: GolfRoundRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    val prevGolfRounds: StateFlow<List<GolfRoundEntity>> = repository.golfRounds
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed((5000)),
            initialValue = emptyList()
        )
    val uiState: StateFlow<HomeScreenUiState> = repository.golfRounds
        .map { rounds ->
            val recentSummaries = rounds.map { entity ->
                RoundSummary(
                    id = entity.id,
                    courseName = entity.courseName,
                    date = entity.datePlayed,
                    score = entity.score,
                    scoreToPar = entity.score - entity.par
                )
            }
            val avgScore = if (rounds.isNotEmpty()) rounds.map { it.score }.average() else null

            HomeScreenUiState(
                stats = PlayerStats(
                    averageScore = avgScore,
                    handicap = null,
                    fairwayPercentage = null
                ),
                recentRounds = recentSummaries
            )
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenUiState()
        )


}