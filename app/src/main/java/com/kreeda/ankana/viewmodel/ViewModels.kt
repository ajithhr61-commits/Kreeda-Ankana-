package com.kreeda.ankana.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kreeda.ankana.data.model.*
import com.kreeda.ankana.data.repository.KreedaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * GroundViewModel — Manages ground listing and booking flows.
 */
@HiltViewModel
class GroundViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val grounds: StateFlow<List<Ground>> = repository.getAllGrounds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedGround = MutableStateFlow<Ground?>(null)
    val selectedGround: StateFlow<Ground?> = _selectedGround.asStateFlow()

    private val _bookingSuccess = MutableSharedFlow<Boolean>()
    val bookingSuccess: SharedFlow<Boolean> = _bookingSuccess.asSharedFlow()

    private val _existingBookings = MutableStateFlow<List<Booking>>(emptyList())
    val existingBookings: StateFlow<List<Booking>> = _existingBookings.asStateFlow()

    fun loadGround(groundId: String) {
        viewModelScope.launch {
            repository.getGroundById(groundId).collect { ground ->
                _selectedGround.value = ground
            }
        }
    }

    fun loadBookingsForDate(groundId: String, date: String) {
        viewModelScope.launch {
            repository.getBookingsForGroundOnDate(groundId, date).collect { bookings ->
                _existingBookings.value = bookings
            }
        }
    }

    fun createBooking(
        groundId: String,
        groundName: String,
        sport: String,
        date: String,
        startTime: String,
        endTime: String,
        teamName: String
    ) {
        viewModelScope.launch {
            val booking = Booking(
                id = UUID.randomUUID().toString(),
                groundId = groundId,
                groundName = groundName,
                userId = "user_001",
                userName = "Arjun Patil",
                teamName = teamName,
                sport = sport,
                date = date,
                startTime = startTime,
                endTime = endTime,
                status = "confirmed"
            )
            repository.saveBooking(booking)

            // Create notification
            val notification = AppNotification(
                id = UUID.randomUUID().toString(),
                title = "Booking Confirmed! ✅",
                message = "Your booking at $groundName on $date ($startTime-$endTime) is confirmed.",
                type = "booking"
            )
            repository.saveNotification(notification)

            _bookingSuccess.emit(true)
        }
    }
}

/**
 * ChallengeViewModel — Manages challenge board operations.
 */
@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val challenges: StateFlow<List<Challenge>> = repository.getAllChallenges()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _challengeCreated = MutableSharedFlow<Boolean>()
    val challengeCreated: SharedFlow<Boolean> = _challengeCreated.asSharedFlow()

    fun createChallenge(
        teamName: String,
        sport: String,
        matchDate: String,
        groundName: String,
        description: String
    ) {
        viewModelScope.launch {
            val challenge = Challenge(
                id = UUID.randomUUID().toString(),
                teamName = teamName,
                teamId = "team_001",
                sport = sport,
                matchDate = matchDate,
                groundName = groundName,
                description = description,
                status = "open"
            )
            repository.saveChallenge(challenge)
            _challengeCreated.emit(true)
        }
    }

    fun acceptChallenge(challenge: Challenge) {
        viewModelScope.launch {
            repository.updateChallenge(
                challenge.copy(
                    status = "accepted",
                    acceptedByTeam = "Shivpur Warriors",
                    acceptedByTeamId = "team_001"
                )
            )
            val notification = AppNotification(
                id = UUID.randomUUID().toString(),
                title = "Challenge Accepted! 🤝",
                message = "You accepted ${challenge.teamName}'s ${challenge.sport} challenge for ${challenge.matchDate}.",
                type = "challenge"
            )
            repository.saveNotification(notification)
        }
    }
}

/**
 * TeamViewModel — Manages team listing, creation, and details.
 */
@HiltViewModel
class TeamViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val teams: StateFlow<List<Team>> = repository.getAllTeams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> = _selectedTeam.asStateFlow()

    private val _teamCreated = MutableSharedFlow<Boolean>()
    val teamCreated: SharedFlow<Boolean> = _teamCreated.asSharedFlow()

    val teamResults: StateFlow<List<MatchResult>> = repository.getAllResults()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun loadTeam(teamId: String) {
        viewModelScope.launch {
            repository.getTeamById(teamId).collect { team ->
                _selectedTeam.value = team
            }
        }
    }

    fun createTeam(name: String, sport: String, village: String) {
        viewModelScope.launch {
            val team = Team(
                id = UUID.randomUUID().toString(),
                name = name,
                sport = sport,
                village = village,
                captainId = "user_001"
            )
            repository.saveTeam(team)
            _teamCreated.emit(true)
        }
    }
}

/**
 * ScoreViewModel — Manages match results / score wall.
 */
@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val results: StateFlow<List<MatchResult>> = repository.getAllResults()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _scorePosted = MutableSharedFlow<Boolean>()
    val scorePosted: SharedFlow<Boolean> = _scorePosted.asSharedFlow()

    fun postScore(
        team1Name: String,
        team1Score: String,
        team2Name: String,
        team2Score: String,
        sport: String,
        groundName: String,
        matchDate: String,
        mvpPlayer: String,
        highlights: String
    ) {
        viewModelScope.launch {
            val result = MatchResult(
                id = UUID.randomUUID().toString(),
                team1Name = team1Name,
                team1Id = "",
                team1Score = team1Score,
                team2Name = team2Name,
                team2Id = "",
                team2Score = team2Score,
                sport = sport,
                groundName = groundName,
                matchDate = matchDate,
                mvpPlayerName = mvpPlayer,
                highlights = highlights,
                winnerTeamId = ""
            )
            repository.saveResult(result)
            _scorePosted.emit(true)
        }
    }
}

/**
 * NotificationViewModel — Manages in-app notifications.
 */
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val notifications: StateFlow<List<AppNotification>> = repository.getAllNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadCount: StateFlow<Int> = repository.getUnreadCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun markAsRead(notificationId: String) {
        viewModelScope.launch { repository.markNotificationRead(notificationId) }
    }

    fun markAllAsRead() {
        viewModelScope.launch { repository.markAllNotificationsRead() }
    }
}

/**
 * SettingsViewModel — Manages app settings and user profile.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = repository.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateProfile(user: User) {
        viewModelScope.launch { repository.updateUser(user) }
    }

    fun logout() {
        viewModelScope.launch { repository.clearUsers() }
    }
}

/**
 * CalendarViewModel — Manages booking calendar view.
 */
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    val allBookings: StateFlow<List<Booking>> = repository.getAllBookings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDateBookings = MutableStateFlow<List<Booking>>(emptyList())
    val selectedDateBookings: StateFlow<List<Booking>> = _selectedDateBookings.asStateFlow()

    fun loadBookingsForDate(date: String) {
        viewModelScope.launch {
            repository.getBookingsForDate(date).collect { bookings ->
                _selectedDateBookings.value = bookings
            }
        }
    }
}
