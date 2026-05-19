package com.kreeda.ankana.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kreeda.ankana.data.model.*
import com.kreeda.ankana.ui.components.*
import com.kreeda.ankana.ui.theme.*
import com.kreeda.ankana.viewmodel.DashboardViewModel

/**
 * DashboardScreen — The main hub showing upcoming matches,
 * bookings, challenges, quick actions, and latest results.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToBooking: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToChallenges: () -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToScoreWall: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPostScore: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    val bookings by viewModel.upcomingBookings.collectAsState()
    val challenges by viewModel.openChallenges.collectAsState()
    val results by viewModel.latestResults.collectAsState()
    val unreadNotifs by viewModel.unreadNotifications.collectAsState()

    if (isLoading) {
        LoadingScreen("Preparing your sports arena...")
        return
    }

    Scaffold(
        topBar = {
            // Custom dashboard top bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hey, ${user?.name?.split(" ")?.first() ?: "Player"} 👋",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user?.village ?: "Your Village Sports Arena",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row {
                        // Notifications bell with badge
                        BadgedBox(
                            badge = {
                                if (unreadNotifs > 0) {
                                    Badge { Text(unreadNotifs.toString()) }
                                }
                            }
                        ) {
                            IconButton(onClick = onNavigateToNotifications) {
                                Icon(Icons.Outlined.Notifications, "Notifications")
                            }
                        }
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(Icons.Outlined.Settings, "Settings")
                        }
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ── Hero Banner ─────────────────────────────────────
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Orange60, Orange40)
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Text(
                                text = "🏏 क्रीडा अंकणा",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "Book grounds • Challenge teams • Track scores",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        "${bookings.size} Bookings",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        "${challenges.size} Challenges",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ── Quick Actions ───────────────────────────────────
            item {
                SectionHeader(title = "Quick Actions")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        QuickActionButton(
                            icon = Icons.Filled.Stadium,
                            label = "Book Ground",
                            gradientColors = listOf(Orange60, Orange80),
                            onClick = onNavigateToBooking
                        )
                    }
                    item {
                        QuickActionButton(
                            icon = Icons.Filled.Campaign,
                            label = "Challenge",
                            gradientColors = listOf(Teal50, Teal80),
                            onClick = onNavigateToChallenges
                        )
                    }
                    item {
                        QuickActionButton(
                            icon = Icons.Filled.CalendarMonth,
                            label = "Calendar",
                            gradientColors = listOf(Gold60, Gold80),
                            onClick = onNavigateToCalendar
                        )
                    }
                    item {
                        QuickActionButton(
                            icon = Icons.Filled.Scoreboard,
                            label = "Post Score",
                            gradientColors = listOf(LiveGreen, Color(0xFF81C784)),
                            onClick = onNavigateToPostScore
                        )
                    }
                    item {
                        QuickActionButton(
                            icon = Icons.Filled.Groups,
                            label = "My Team",
                            gradientColors = listOf(Color(0xFF9C27B0), Color(0xFFCE93D8)),
                            onClick = onNavigateToTeams
                        )
                    }
                }
            }

            // ── Upcoming Bookings ───────────────────────────────
            item {
                SectionHeader(
                    title = "📅 Upcoming Bookings",
                    actionText = "View All",
                    onAction = onNavigateToCalendar
                )
            }
            if (bookings.isEmpty()) {
                item {
                    EmptyStateView(
                        icon = Icons.Outlined.EventBusy,
                        title = "No upcoming bookings",
                        subtitle = "Book a ground to start playing!"
                    )
                }
            } else {
                items(bookings.take(3)) { booking ->
                    BookingCard(booking = booking, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

            // ── Challenge Board Preview ─────────────────────────
            item {
                SectionHeader(
                    title = "⚔️ Challenge Board",
                    actionText = "See All",
                    onAction = onNavigateToChallenges
                )
            }
            if (challenges.isEmpty()) {
                item {
                    EmptyStateView(
                        icon = Icons.Outlined.SportsKabaddi,
                        title = "No open challenges",
                        subtitle = "Post a challenge to find opponents!"
                    )
                }
            } else {
                items(challenges.take(2)) { challenge ->
                    ChallengePreviewCard(
                        challenge = challenge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // ── Latest Results ──────────────────────────────────
            item {
                SectionHeader(
                    title = "🏆 Latest Results",
                    actionText = "Score Wall",
                    onAction = onNavigateToScoreWall
                )
            }
            items(results.take(2)) { result ->
                MatchResultCard(
                    team1Name = result.team1Name,
                    team1Score = result.team1Score,
                    team2Name = result.team2Name,
                    team2Score = result.team2Score,
                    sport = result.sport,
                    ground = result.groundName,
                    date = result.matchDate,
                    mvp = result.mvpPlayerName,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Bottom spacer
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

/**
 * BookingCard — Compact card for upcoming booking display.
 */
@Composable
private fun BookingCard(booking: Booking, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time indicator
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Filled.AccessTime,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = booking.groundName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${booking.date} • ${booking.startTime} - ${booking.endTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${booking.teamName} • ${booking.sport}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            StatusBadge(status = booking.status)
        }
    }
}

/**
 * ChallengePreviewCard — Card for challenge board preview on dashboard.
 */
@Composable
private fun ChallengePreviewCard(challenge: Challenge, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = challenge.teamName.first().toString(),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = challenge.teamName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = challenge.matchDate,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Row {
                    SportChip(sport = challenge.sport)
                    Spacer(modifier = Modifier.width(4.dp))
                    StatusBadge(status = challenge.status)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
