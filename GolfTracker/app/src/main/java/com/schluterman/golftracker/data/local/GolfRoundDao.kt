package com.schluterman.golftracker.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.schluterman.golftracker.domain.GolfRoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GolfRoundDao {

    @Query("SELECT * FROM golf_rounds ORDER BY id DESC")
    fun getAllRounds(): Flow<List<GolfRoundEntity>>

    @Insert
    suspend fun insertRound(round: GolfRoundEntity)

    @Update
    suspend fun updateRound(round: GolfRoundEntity)

    @Delete
    suspend fun deleteRound(round: GolfRoundEntity)
}

@Database(entities = [GolfRoundEntity::class], version = 1, exportSchema = false)
abstract class GolfDatabase : RoomDatabase() {
    abstract fun golfRoundDao(): GolfRoundDao

    companion object {
        @Volatile
        private var INSTANCE: GolfDatabase? = null

        fun getDatabase(context: Context): GolfDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GolfDatabase::class.java,
                    "golf_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}