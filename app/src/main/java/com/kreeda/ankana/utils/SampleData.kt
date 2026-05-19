package com.kreeda.ankana.utils

import com.kreeda.ankana.data.model.*
import java.util.UUID

/**
 * SampleData — Seed data for demonstrating the app.
 * Provides realistic village sports data for all modules.
 */
object SampleData {

    val currentUser = User(
        id = "user_001",
        name = "Arjun Patil",
        email = "arjun@kreeda.app",
        phone = "+91 98765 43210",
        village = "Shivpur",
        sportsType = "Cricket",
        teamName = "Shivpur Warriors",
        isAdmin = false
    )

    val grounds = listOf(
        Ground(
            id = "ground_001",
            name = "Shivpur Maidan",
            location = "Near Gram Panchayat, Shivpur",
            village = "Shivpur",
            sportTypes = "Cricket,Volleyball,Kabaddi",
            description = "Large open ground with floodlights. Suitable for cricket and volleyball matches.",
            isAvailable = true,
            openTime = "06:00",
            closeTime = "21:00"
        ),
        Ground(
            id = "ground_002",
            name = "Rampur Sports Complex",
            location = "Behind Government School, Rampur",
            village = "Rampur",
            sportTypes = "Cricket,Football",
            description = "Well-maintained turf ground with pavilion seating. Ideal for football and cricket.",
            isAvailable = true,
            openTime = "07:00",
            closeTime = "20:00"
        ),
        Ground(
            id = "ground_003",
            name = "Laxmi Nagar Ground",
            location = "Laxmi Nagar Colony, Shivpur",
            village = "Shivpur",
            sportTypes = "Volleyball,Kabaddi",
            description = "Compact ground ideal for kabaddi and volleyball tournaments.",
            isAvailable = true,
            openTime = "06:00",
            closeTime = "19:00"
        ),
        Ground(
            id = "ground_004",
            name = "Nandgaon Krida Bhumi",
            location = "Main Road, Nandgaon",
            village = "Nandgaon",
            sportTypes = "Cricket,Football,Kho-Kho",
            description = "Heritage village ground with natural grass pitch. Hosts annual tournaments.",
            isAvailable = true,
            openTime = "06:00",
            closeTime = "20:00"
        )
    )

    val teams = listOf(
        Team(
            id = "team_001",
            name = "Shivpur Warriors",
            sport = "Cricket",
            village = "Shivpur",
            captainId = "user_001",
            wins = 12,
            losses = 3,
            draws = 2,
            playerIds = "user_001,user_002,user_003,user_004,user_005"
        ),
        Team(
            id = "team_002",
            name = "Rampur Riders",
            sport = "Cricket",
            village = "Rampur",
            captainId = "user_010",
            wins = 9,
            losses = 5,
            draws = 1,
            playerIds = "user_010,user_011,user_012,user_013"
        ),
        Team(
            id = "team_003",
            name = "Nandgaon Titans",
            sport = "Football",
            village = "Nandgaon",
            captainId = "user_020",
            wins = 15,
            losses = 2,
            draws = 4,
            playerIds = "user_020,user_021,user_022,user_023"
        ),
        Team(
            id = "team_004",
            name = "Shivpur Spikers",
            sport = "Volleyball",
            village = "Shivpur",
            captainId = "user_030",
            wins = 8,
            losses = 4,
            draws = 0,
            playerIds = "user_030,user_031,user_032"
        )
    )

    val bookings = listOf(
        Booking(
            id = "booking_001",
            groundId = "ground_001",
            groundName = "Shivpur Maidan",
            userId = "user_001",
            userName = "Arjun Patil",
            teamName = "Shivpur Warriors",
            sport = "Cricket",
            date = "2024-03-20",
            startTime = "09:00",
            endTime = "12:00",
            status = "confirmed"
        ),
        Booking(
            id = "booking_002",
            groundId = "ground_002",
            groundName = "Rampur Sports Complex",
            userId = "user_010",
            userName = "Vikram Singh",
            teamName = "Rampur Riders",
            sport = "Cricket",
            date = "2024-03-20",
            startTime = "14:00",
            endTime = "17:00",
            status = "confirmed"
        ),
        Booking(
            id = "booking_003",
            groundId = "ground_001",
            groundName = "Shivpur Maidan",
            userId = "user_030",
            userName = "Ravi Kumar",
            teamName = "Shivpur Spikers",
            sport = "Volleyball",
            date = "2024-03-21",
            startTime = "16:00",
            endTime = "18:00",
            status = "confirmed"
        )
    )

    val challenges = listOf(
        Challenge(
            id = "challenge_001",
            teamName = "Shivpur Warriors",
            teamId = "team_001",
            sport = "Cricket",
            matchDate = "2024-03-25",
            groundName = "Shivpur Maidan",
            description = "T20 match challenge! We're ready to take on any team. Best of 20 overs. Come prove your skills! 🏏",
            status = "open"
        ),
        Challenge(
            id = "challenge_002",
            teamName = "Nandgaon Titans",
            teamId = "team_003",
            sport = "Football",
            matchDate = "2024-03-26",
            groundName = "Nandgaon Krida Bhumi",
            description = "Friendly football match – 7-a-side. Looking for a strong opponent team. Evening slot preferred. ⚽",
            status = "open"
        ),
        Challenge(
            id = "challenge_003",
            teamName = "Rampur Riders",
            teamId = "team_002",
            sport = "Cricket",
            matchDate = "2024-03-22",
            groundName = "Rampur Sports Complex",
            description = "One-day match challenge. Full 50 overs. Serious teams only! 💪",
            status = "accepted",
            acceptedByTeam = "Shivpur Warriors",
            acceptedByTeamId = "team_001"
        )
    )

    val matchResults = listOf(
        MatchResult(
            id = "result_001",
            team1Name = "Shivpur Warriors",
            team1Id = "team_001",
            team1Score = "186/4",
            team2Name = "Rampur Riders",
            team2Id = "team_002",
            team2Score = "172/8",
            sport = "Cricket",
            groundName = "Shivpur Maidan",
            matchDate = "2024-03-15",
            mvpPlayerName = "Arjun Patil",
            highlights = "Arjun hit 78 off 42 balls. Warriors bowling was lethal in the death overs!",
            winnerTeamId = "team_001"
        ),
        MatchResult(
            id = "result_002",
            team1Name = "Nandgaon Titans",
            team1Id = "team_003",
            team1Score = "3",
            team2Name = "Shivpur Spikers",
            team2Id = "team_004",
            team2Score = "1",
            sport = "Football",
            groundName = "Nandgaon Krida Bhumi",
            matchDate = "2024-03-12",
            mvpPlayerName = "Suresh Jadhav",
            highlights = "Suresh scored a hat-trick! Titans dominated possession throughout.",
            winnerTeamId = "team_003"
        ),
        MatchResult(
            id = "result_003",
            team1Name = "Shivpur Spikers",
            team1Id = "team_004",
            team1Score = "25-21, 18-25, 25-19",
            team2Name = "Rampur Riders",
            team2Id = "team_002",
            team2Score = "21-25, 25-18, 19-25",
            sport = "Volleyball",
            groundName = "Laxmi Nagar Ground",
            matchDate = "2024-03-10",
            mvpPlayerName = "Ravi Kumar",
            highlights = "Nail-biting 3-set thriller! Ravi's smashes were unstoppable in the final set.",
            winnerTeamId = "team_004"
        )
    )

    val notifications = listOf(
        AppNotification(
            id = "notif_001",
            title = "Booking Confirmed! ✅",
            message = "Your booking at Shivpur Maidan on March 20 (9:00-12:00) is confirmed.",
            type = "booking",
            isRead = false
        ),
        AppNotification(
            id = "notif_002",
            title = "New Challenge! 🏏",
            message = "Rampur Riders have posted a cricket challenge for March 22. Accept now!",
            type = "challenge",
            isRead = false
        ),
        AppNotification(
            id = "notif_003",
            title = "Match Tomorrow ⏰",
            message = "Reminder: You have a match at Shivpur Maidan tomorrow at 9:00 AM.",
            type = "match",
            isRead = true
        ),
        AppNotification(
            id = "notif_004",
            title = "Tournament Announcement 🏆",
            message = "Annual Shivpur Premier League registration starts March 25! Register your team now.",
            type = "announcement",
            isRead = false
        )
    )
}
