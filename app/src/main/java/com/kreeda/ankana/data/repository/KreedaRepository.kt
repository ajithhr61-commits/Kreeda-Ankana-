package com.kreeda.ankana.data.repository

import com.kreeda.ankana.data.local.*
import com.kreeda.ankana.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * KreedaRepository — Single source of truth for all app data.
 * Abstracts Room DB access; will later integrate Firebase as remote source.
 */
@Singleton
class KreedaRepository @Inject constructor(
    private val userDao: UserDao,
    private val teamDao: TeamDao,
    private val groundDao: GroundDao,
    private val bookingDao: BookingDao,
    private val challengeDao: ChallengeDao,
    private val matchResultDao: MatchResultDao,
    private val notificationDao: NotificationDao
) {
    // ── User ────────────────────────────────────────────────
    fun getCurrentUser(): Flow<User?> = userDao.getCurrentUser()
    suspend fun saveUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun clearUsers() = userDao.deleteAllUsers()

    // ── Teams ───────────────────────────────────────────────
    fun getAllTeams(): Flow<List<Team>> = teamDao.getAllTeams()
    fun getTeamById(id: String): Flow<Team?> = teamDao.getTeamById(id)
    fun getTeamsByUser(userId: String): Flow<List<Team>> = teamDao.getTeamsByUser(userId)
    suspend fun saveTeam(team: Team) = teamDao.insertTeam(team)
    suspend fun updateTeam(team: Team) = teamDao.updateTeam(team)
    suspend fun deleteTeam(team: Team) = teamDao.deleteTeam(team)
    suspend fun saveTeams(teams: List<Team>) = teamDao.insertTeams(teams)

    // ── Grounds ─────────────────────────────────────────────
    fun getAllGrounds(): Flow<List<Ground>> = groundDao.getAllGrounds()
    fun getGroundById(id: String): Flow<Ground?> = groundDao.getGroundById(id)
    fun getAvailableGrounds(): Flow<List<Ground>> = groundDao.getAvailableGrounds()
    suspend fun saveGround(ground: Ground) = groundDao.insertGround(ground)
    suspend fun saveGrounds(grounds: List<Ground>) = groundDao.insertGrounds(grounds)

    // ── Bookings ────────────────────────────────────────────
    fun getAllBookings(): Flow<List<Booking>> = bookingDao.getAllBookings()
    fun getBookingsByUser(userId: String): Flow<List<Booking>> = bookingDao.getBookingsByUser(userId)
    fun getBookingsForGroundOnDate(groundId: String, date: String): Flow<List<Booking>> =
        bookingDao.getBookingsForGroundOnDate(groundId, date)
    fun getBookingsForDate(date: String): Flow<List<Booking>> = bookingDao.getBookingsForDate(date)
    suspend fun saveBooking(booking: Booking) = bookingDao.insertBooking(booking)
    suspend fun updateBooking(booking: Booking) = bookingDao.updateBooking(booking)
    suspend fun saveBookings(bookings: List<Booking>) = bookingDao.insertBookings(bookings)

    // ── Challenges ──────────────────────────────────────────
    fun getAllChallenges(): Flow<List<Challenge>> = challengeDao.getAllChallenges()
    fun getOpenChallenges(): Flow<List<Challenge>> = challengeDao.getOpenChallenges()
    fun getChallengesByTeam(teamId: String): Flow<List<Challenge>> = challengeDao.getChallengesByTeam(teamId)
    suspend fun saveChallenge(challenge: Challenge) = challengeDao.insertChallenge(challenge)
    suspend fun updateChallenge(challenge: Challenge) = challengeDao.updateChallenge(challenge)
    suspend fun saveChallenges(challenges: List<Challenge>) = challengeDao.insertChallenges(challenges)

    // ── Match Results ───────────────────────────────────────
    fun getAllResults(): Flow<List<MatchResult>> = matchResultDao.getAllResults()
    fun getResultsByTeam(teamId: String): Flow<List<MatchResult>> = matchResultDao.getResultsByTeam(teamId)
    suspend fun saveResult(result: MatchResult) = matchResultDao.insertResult(result)
    suspend fun saveResults(results: List<MatchResult>) = matchResultDao.insertResults(results)

    // ── Notifications ───────────────────────────────────────
    fun getAllNotifications(): Flow<List<AppNotification>> = notificationDao.getAllNotifications()
    fun getUnreadCount(): Flow<Int> = notificationDao.getUnreadCount()
    suspend fun saveNotification(notification: AppNotification) = notificationDao.insertNotification(notification)
    suspend fun markNotificationRead(id: String) = notificationDao.markAsRead(id)
    suspend fun markAllNotificationsRead() = notificationDao.markAllAsRead()
    suspend fun saveNotifications(notifications: List<AppNotification>) = notificationDao.insertNotifications(notifications)
}
