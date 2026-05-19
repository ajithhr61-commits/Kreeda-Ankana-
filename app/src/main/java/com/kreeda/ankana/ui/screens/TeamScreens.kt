package com.kreeda.ankana.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kreeda.ankana.data.model.Team
import com.kreeda.ankana.ui.components.*
import com.kreeda.ankana.ui.theme.*
import com.kreeda.ankana.viewmodel.TeamViewModel

/**
 * TeamListScreen — Shows all teams with stats.
 */
@Composable
fun TeamListScreen(
    onNavigateBack: () -> Unit,
    onTeamSelected: (String) -> Unit,
    onCreateTeam: () -> Unit,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val teams by viewModel.teams.collectAsState()

    Scaffold(
        topBar = {
            KreedaTopBar(title = "👥 Teams", onBackClick = onNavigateBack)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateTeam,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.GroupAdd, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Create Team", fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        if (teams.isEmpty()) {
            EmptyStateView(
                icon = Icons.Outlined.Groups,
                title = "No teams yet",
                subtitle = "Create your team to get started!",
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(teams) { team ->
                    TeamCard(
                        team = team,
                        onClick = { onTeamSelected(team.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(72.dp)) }
            }
        }
    }
}

@Composable
private fun TeamCard(team: Team, onClick: () -> Unit) {
    val gradientColors = when (team.sport.lowercase()) {
        "cricket" -> listOf(Orange60, Orange40)
        "football" -> listOf(Teal50, Teal40)
        "volleyball" -> listOf(Gold60, Gold40)
        else -> listOf(LiveGreen, Color(0xFF2E7D32))
    }

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Team avatar
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(16.dp),
                color = gradientColors.first().copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = team.name.split(" ").map { it.first() }.take(2).joinToString(""),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = gradientColors.first()
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SportChip(sport = team.sport)
                    Text(
                        text = "📍 ${team.village}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                // W/L/D stats
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatPill("W", team.wins.toString(), LiveGreen)
                    StatPill("L", team.losses.toString(), ErrorRed)
                    StatPill("D", team.draws.toString(), Neutral50)
                }
            }

            Icon(
                Icons.Filled.ChevronRight,
                null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StatPill(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

/**
 * TeamDetailScreen — Detailed view of a team with stats and history.
 */
@Composable
fun TeamDetailScreen(
    teamId: String,
    onNavigateBack: () -> Unit,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val team by viewModel.selectedTeam.collectAsState()
    val results by viewModel.teamResults.collectAsState()

    LaunchedEffect(teamId) { viewModel.loadTeam(teamId) }

    Scaffold(
        topBar = {
            KreedaTopBar(
                title = team?.name ?: "Team Details",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        if (team == null) {
            LoadingScreen()
            return@Scaffold
        }

        val t = team!!
        val playerCount = t.playerIds.split(",").filter { it.isNotBlank() }.size
        val totalMatches = t.wins + t.losses + t.draws
        val winRate = if (totalMatches > 0) (t.wins.toFloat() / totalMatches * 100).toInt() else 0

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Team header card
            item {
                ElevatedCard(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Orange60, Orange40)
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(80.dp),
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.2f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = t.name.split(" ").map { it.first() }.take(2).joinToString(""),
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = t.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "📍 ${t.village} • ${t.sport}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Stats row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard("Matches", totalMatches.toString(), Modifier.weight(1f))
                    StatCard("Win Rate", "$winRate%", Modifier.weight(1f))
                    StatCard("Players", playerCount.toString(), Modifier.weight(1f))
                }
            }

            // W/L/D detailed stats
            item {
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DetailedStat("Wins", t.wins, LiveGreen)
                        DetailedStat("Losses", t.losses, ErrorRed)
                        DetailedStat("Draws", t.draws, WarningAmber)
                    }
                }
            }

            // Match history
            item {
                SectionHeader(title = "🏆 Match History")
            }

            val teamResults = results.filter { it.team1Id == teamId || it.team2Id == teamId }
            if (teamResults.isEmpty()) {
                item {
                    EmptyStateView(
                        icon = Icons.Outlined.History,
                        title = "No matches played yet",
                        subtitle = "Results will appear here after matches"
                    )
                }
            } else {
                items(teamResults) { result ->
                    MatchResultCard(
                        team1Name = result.team1Name,
                        team1Score = result.team1Score,
                        team2Name = result.team2Name,
                        team2Score = result.team2Score,
                        sport = result.sport,
                        ground = result.groundName,
                        date = result.matchDate,
                        mvp = result.mvpPlayerName
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DetailedStat(label: String, value: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * CreateTeamScreen — Form to create a new team.
 */
@Composable
fun CreateTeamScreen(
    onNavigateBack: () -> Unit,
    onTeamCreated: () -> Unit,
    viewModel: TeamViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var sport by remember { mutableStateOf("Cricket") }
    var village by remember { mutableStateOf("") }
    val sports = listOf("Cricket", "Football", "Volleyball", "Kabaddi", "Kho-Kho")

    LaunchedEffect(Unit) {
        viewModel.teamCreated.collect { if (it) onTeamCreated() }
    }

    Scaffold(
        topBar = {
            KreedaTopBar(title = "Create Team", onBackClick = onNavigateBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Build your dream team! 💪",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Team Name") },
                leadingIcon = { Icon(Icons.Outlined.Groups, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Text("Sport", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sports.take(3).forEach { s ->
                    FilterChip(
                        selected = sport == s,
                        onClick = { sport = s },
                        label = { Text(s, style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sports.drop(3).forEach { s ->
                    FilterChip(
                        selected = sport == s,
                        onClick = { sport = s },
                        label = { Text(s, style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            OutlinedTextField(
                value = village,
                onValueChange = { village = it },
                label = { Text("Village / Town") },
                leadingIcon = { Icon(Icons.Outlined.LocationOn, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.createTeam(name, sport, village) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = name.isNotEmpty() && village.isNotEmpty()
            ) {
                Icon(Icons.Filled.GroupAdd, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Create Team", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
