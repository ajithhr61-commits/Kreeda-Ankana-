package com.kreeda.ankana.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User — Represents a registered user of Kreeda-Ankana.
 * Stored locally in Room for offline access.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val village: String = "",
    val sportsType: String = "",
    val teamName: String = "",
    val profilePhotoUrl: String = "",
    val isAdmin: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Team — Represents a sports team.
 */
@Entity(tableName = "teams")
data class Team(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val sport: String = "",
    val village: String = "",
    val captainId: String = "",
    val logoUrl: String = "",
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
    val playerIds: String = "", // comma-separated player IDs
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Ground — A sports ground available for booking.
 */
@Entity(tableName = "grounds")
data class Ground(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val location: String = "",
    val village: String = "",
    val sportTypes: String = "", // comma-separated: "Cricket,Volleyball,Kabaddi"
    val imageUrl: String = "",
    val description: String = "",
    val isAvailable: Boolean = true,
    val openTime: String = "06:00",
    val closeTime: String = "21:00"
)

/**
 * Booking — A ground reservation by a team/user.
 */
@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey val id: String = "",
    val groundId: String = "",
    val groundName: String = "",
    val userId: String = "",
    val userName: String = "",
    val teamName: String = "",
    val sport: String = "",
    val date: String = "", // "2024-03-15"
    val startTime: String = "", // "09:00"
    val endTime: String = "", // "11:00"
    val status: String = "confirmed", // confirmed, cancelled, pending
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Challenge — A match challenge posted on the challenge board.
 */
@Entity(tableName = "challenges")
data class Challenge(
    @PrimaryKey val id: String = "",
    val teamName: String = "",
    val teamId: String = "",
    val sport: String = "",
    val matchDate: String = "",
    val groundName: String = "",
    val description: String = "",
    val status: String = "open", // open, accepted, completed, expired
    val acceptedByTeam: String = "",
    val acceptedByTeamId: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * MatchResult — Final score and details of a completed match.
 */
@Entity(tableName = "match_results")
data class MatchResult(
    @PrimaryKey val id: String = "",
    val team1Name: String = "",
    val team1Id: String = "",
    val team1Score: String = "",
    val team2Name: String = "",
    val team2Id: String = "",
    val team2Score: String = "",
    val sport: String = "",
    val groundName: String = "",
    val matchDate: String = "",
    val mvpPlayerName: String = "",
    val highlights: String = "",
    val winnerTeamId: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * AppNotification — In-app notification record.
 */
@Entity(tableName = "notifications")
data class AppNotification(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val message: String = "",
    val type: String = "", // booking, challenge, match, announcement
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
