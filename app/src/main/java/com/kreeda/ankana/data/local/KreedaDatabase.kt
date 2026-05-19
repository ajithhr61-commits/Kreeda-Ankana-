package com.kreeda.ankana.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kreeda.ankana.data.model.*

/**
 * KreedaDatabase — Room database for Kreeda-Ankana.
 * Contains all tables for offline-first data storage.
 * Version 1 — initial schema.
 */
@Database(
    entities = [
        User::class,
        Team::class,
        Ground::class,
        Booking::class,
        Challenge::class,
        MatchResult::class,
        AppNotification::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KreedaDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun teamDao(): TeamDao
    abstract fun groundDao(): GroundDao
    abstract fun bookingDao(): BookingDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun matchResultDao(): MatchResultDao
    abstract fun notificationDao(): NotificationDao
}
