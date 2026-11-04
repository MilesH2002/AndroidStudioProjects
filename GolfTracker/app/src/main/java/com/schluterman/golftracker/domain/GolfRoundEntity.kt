package com.schluterman.golftracker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "golf_rounds")
data class GolfRoundEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val courseName: String,
    val score: Int,
    val par: Int = 72,
    val holes: Int = 18,
    val datePlayed: String = ""
)