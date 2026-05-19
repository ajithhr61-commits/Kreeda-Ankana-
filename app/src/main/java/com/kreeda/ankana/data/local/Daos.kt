package com.kreeda.ankana.data.local

import androidx.room.*
import com.kreeda.ankana.data.model.*
import kotlinx.coroutines.flow.Flow

/**
 * UserDao — Data Access Object for User entity.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserById(userId: String): Flow<User?>

    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}

/**
 * TeamDao — Data Access Object for Team entity.
 */
@Dao
interface TeamDao {
    @Query("SELECT * FROM teams ORDER BY name ASC")
    fun getAllTeams(): Flow<List<Team>>

    @Query("SELECT * FROM teams WHERE id = :teamId LIMIT 1")
    fun getTeamById(teamId: String): Flow<Team?>

    @Query("SELECT * FROM teams WHERE captainId = :userId")
    fun getTeamsByUser(userId: String): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Update
    suspend fun updateTeam(team: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<Team>)
}

/**
 * GroundDao — Data Access Object for Ground entity.
 */
@Dao
interface GroundDao {
    @Query("SELECT * FROM grounds ORDER BY name ASC")
    fun getAllGrounds(): Flow<List<Ground>>

    @Query("SELECT * FROM grounds WHERE id = :groundId LIMIT 1")
    fun getGroundById(groundId: String): Flow<Ground?>

    @Query("SELECT * FROM grounds WHERE isAvailable = 1")
    fun getAvailableGrounds(): Flow<List<Ground>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGround(ground: Ground)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrounds(grounds: List<Ground>)

    @Delete
    suspend fun deleteGround(ground: Ground)
}

/**
 * BookingDao — Data Access Object for Booking entity.
 */
@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY date DESC, startTime ASC")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE userId = :userId ORDER BY date DESC")
    fun getBookingsByUser(userId: String): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE groundId = :groundId AND date = :date AND status != 'cancelled'")
    fun getBookingsForGroundOnDate(groundId: String, date: String): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE date = :date AND status != 'cancelled' ORDER BY startTime ASC")
    fun getBookingsForDate(date: String): Flow<List<Booking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking)

    @Update
    suspend fun updateBooking(booking: Booking)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookings(bookings: List<Booking>)
}

/**
 * ChallengeDao — Data Access Object for Challenge entity.
 */
@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges ORDER BY createdAt DESC")
    fun getAllChallenges(): Flow<List<Challenge>>

    @Query("SELECT * FROM challenges WHERE status = 'open' ORDER BY createdAt DESC")
    fun getOpenChallenges(): Flow<List<Challenge>>

    @Query("SELECT * FROM challenges WHERE teamId = :teamId ORDER BY createdAt DESC")
    fun getChallengesByTeam(teamId: String): Flow<List<Challenge>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challenge: Challenge)

    @Update
    suspend fun updateChallenge(challenge: Challenge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenges(challenges: List<Challenge>)
}

/**
 * MatchResultDao — Data Access Object for MatchResult entity.
 */
@Dao
interface MatchResultDao {
    @Query("SELECT * FROM match_results ORDER BY createdAt DESC")
    fun getAllResults(): Flow<List<MatchResult>>

    @Query("SELECT * FROM match_results WHERE team1Id = :teamId OR team2Id = :teamId ORDER BY createdAt DESC")
    fun getResultsByTeam(teamId: String): Flow<List<MatchResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: MatchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<MatchResult>)
}

/**
 * NotificationDao — Data Access Object for AppNotification entity.
 */
@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    fun getAllNotifications(): Flow<List<AppNotification>>

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: AppNotification)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markAsRead(notificationId: String)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<AppNotification>)
}
