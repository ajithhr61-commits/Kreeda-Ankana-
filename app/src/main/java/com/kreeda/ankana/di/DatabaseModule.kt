package com.kreeda.ankana.di

import android.content.Context
import androidx.room.Room
import com.kreeda.ankana.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DatabaseModule — Hilt module providing Room database and all DAOs.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KreedaDatabase {
        return Room.databaseBuilder(
            context,
            KreedaDatabase::class.java,
            "kreeda_ankana_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideUserDao(db: KreedaDatabase): UserDao = db.userDao()
    @Provides fun provideTeamDao(db: KreedaDatabase): TeamDao = db.teamDao()
    @Provides fun provideGroundDao(db: KreedaDatabase): GroundDao = db.groundDao()
    @Provides fun provideBookingDao(db: KreedaDatabase): BookingDao = db.bookingDao()
    @Provides fun provideChallengeDao(db: KreedaDatabase): ChallengeDao = db.challengeDao()
    @Provides fun provideMatchResultDao(db: KreedaDatabase): MatchResultDao = db.matchResultDao()
    @Provides fun provideNotificationDao(db: KreedaDatabase): NotificationDao = db.notificationDao()
}
