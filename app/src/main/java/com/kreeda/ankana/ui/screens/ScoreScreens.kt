package com.kreeda.ankana.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kreeda.ankana.ui.components.*
import com.kreeda.ankana.viewmodel.ScoreViewModel

/**
 * ScoreWallScreen — Public feed of match results.
 */
@Composable
fun ScoreWallScreen(
    onNavigateBack: () -> Unit,
    onPostScore: () -> Unit,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val results by viewModel.results.collectAsState()

    Scaffold(
        topBar = {
            KreedaTopBar(title = "🏆 Score Wall", onBackClick = onNavigateBack)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onPostScore,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Scoreboard, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Post Score", fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        if (results.isEmpty()) {
            EmptyStateView(
                icon = Icons.Outlined.Scoreboard,
                title = "No match results yet",
                subtitle = "Post a score after your match!",
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
                items(results) { result ->
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
                item { Spacer(modifier = Modifier.height(72.dp)) }
            }
        }
    }
}

/**
 * PostScoreScreen — Form to post match results.
 */
@Composable
fun PostScoreScreen(
    onNavigateBack: () -> Unit,
    onScorePosted: () -> Unit,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    var team1Name by remember { mutableStateOf("") }
    var team1Score by remember { mutableStateOf("") }
    var team2Name by remember { mutableStateOf("") }
    var team2Score by remember { mutableStateOf("") }
    var sport by remember { mutableStateOf("Cricket") }
    var groundName by remember { mutableStateOf("") }
    var matchDate by remember { mutableStateOf("") }
    var mvpPlayer by remember { mutableStateOf("") }
    var highlights by remember { mutableStateOf("") }

    val sports = listOf("Cricket", "Football", "Volleyball", "Kabaddi")

    LaunchedEffect(Unit) {
        viewModel.scorePosted.collect { if (it) onScorePosted() }
    }

    Scaffold(
        topBar = {
            KreedaTopBar(title = "Post Match Score", onBackClick = onNavigateBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Record your match results! 📊",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Sport selection
            Text("Sport", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sports.forEach { s ->
                    FilterChip(
                        selected = sport == s,
                        onClick = { sport = s },
                        label = { Text(s, style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            HorizontalDivider()

            // Team 1
            Text("Team 1", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = team1Name,
                    onValueChange = { team1Name = it },
                    label = { Text("Team Name") },
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = team1Score,
                    onValueChange = { team1Score = it },
                    label = { Text("Score") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
            }

            // Team 2
            Text("Team 2", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = team2Name,
                    onValueChange = { team2Name = it },
                    label = { Text("Team Name") },
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = team2Score,
                    onValueChange = { team2Score = it },
                    label = { Text("Score") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
            }

            HorizontalDivider()

            OutlinedTextField(
                value = groundName,
                onValueChange = { groundName = it },
                label = { Text("Ground Name") },
                leadingIcon = { Icon(Icons.Outlined.LocationOn, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = matchDate,
                onValueChange = { matchDate = it },
                label = { Text("Match Date (YYYY-MM-DD)") },
                leadingIcon = { Icon(Icons.Outlined.CalendarToday, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = mvpPlayer,
                onValueChange = { mvpPlayer = it },
                label = { Text("MVP Player") },
                leadingIcon = { Icon(Icons.Outlined.Star, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = highlights,
                onValueChange = { highlights = it },
                label = { Text("Match Highlights") },
                leadingIcon = { Icon(Icons.Outlined.Description, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.postScore(
                        team1Name, team1Score, team2Name, team2Score,
                        sport, groundName, matchDate, mvpPlayer, highlights
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = team1Name.isNotEmpty() && team2Name.isNotEmpty()
                        && team1Score.isNotEmpty() && team2Score.isNotEmpty()
            ) {
                Icon(Icons.Filled.Scoreboard, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Post Score", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
