package com.schluterman.golftracker

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.schluterman.golftracker.data.local.GolfRoundDao
import com.schluterman.golftracker.data.local.GolfDatabase
import com.schluterman.golftracker.data.local.GolfRoundRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGolfDatabase(@ApplicationContext context: Context): GolfDatabase {
        return Room.databaseBuilder(
            context,
            GolfDatabase::class.java,
            "golf_database"
        ).build()
    }

    @Provides
    fun provideGolfRoundDao(database: GolfDatabase): GolfRoundDao {
        return database.golfRoundDao()
    }

    @Provides
    @Singleton
    fun provideGolfRoundRepository(dao: GolfRoundDao): GolfRoundRepository {
        return GolfRoundRepository(dao)
    }
}