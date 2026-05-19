package com.kreeda.ankana.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kreeda.ankana.data.model.Challenge
import com.kreeda.ankana.ui.components.*
import com.kreeda.ankana.ui.theme.*
import com.kreeda.ankana.viewmodel.ChallengeViewModel

/**
 * ChallengeBoardScreen — Real-time challenge board showing all open/accepted challenges.
 */
@Composable
fun ChallengeBoardScreen(
    onNavigateBack: () -> Unit,
    onCreateChallenge: () -> Unit,
    viewModel: ChallengeViewModel = hiltViewModel()
) {
    val challenges by viewModel.challenges.collectAsState()

    Scaffold(
        topBar = {
            KreedaTopBar(
                title = "⚔️ Challenge Board",
                onBackClick = onNavigateBack,
                actions = {
                    IconButton(onClick = onCreateChallenge) {
                        Icon(Icons.Filled.Add, "Create Challenge")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateChallenge,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Campaign, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Post Challenge", fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        if (challenges.isEmpty()) {
            EmptyStateView(
                icon = Icons.Outlined.SportsKabaddi,
                title = "No challenges yet",
                subtitle = "Be the first to post a challenge!",
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
                items(challenges) { challenge ->
                    ChallengeCard(
                        challenge = challenge,
                        onAccept = { viewModel.acceptChallenge(challenge) }
                    )
                }
                item { Spacer(modifier = Modifier.height(72.dp)) } // FAB clearance
            }
        }
    }
}

@Composable
private fun ChallengeCard(challenge: Challenge, onAccept: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Team info + status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = challenge.teamName.take(2).uppercase(),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = challenge.teamName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SportChip(sport = challenge.sport)
                        }
                    }
                }
                StatusBadge(status = challenge.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Details row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.CalendarToday,
                        null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = challenge.matchDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = challenge.groundName,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Accepted by info
            if (challenge.status == "accepted" && challenge.acceptedByTeam.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Teal50.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Handshake,
                            null,
                            tint = Teal50,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Accepted by ${challenge.acceptedByTeam}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Teal50
                        )
                    }
                }
            }

            // Accept button for open challenges
            if (challenge.status == "open") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { /* Reply */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Outlined.Chat, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reply")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onAccept,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Check, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Accept", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * CreateChallengeScreen — Form to post a new match challenge.
 */
@Composable
fun CreateChallengeScreen(
    onNavigateBack: () -> Unit,
    onChallengeCreated: () -> Unit,
    viewModel: ChallengeViewModel = hiltViewModel()
) {
    var teamName by remember { mutableStateOf("Shivpur Warriors") }
    var sport by remember { mutableStateOf("Cricket") }
    var matchDate by remember { mutableStateOf("2024-03-30") }
    var groundName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val sports = listOf("Cricket", "Football", "Volleyball", "Kabaddi", "Kho-Kho")
    val grounds = listOf("Shivpur Maidan", "Rampur Sports Complex", "Laxmi Nagar Ground", "Nandgaon Krida Bhumi")

    LaunchedEffect(Unit) {
        viewModel.challengeCreated.collect { if (it) onChallengeCreated() }
    }

    Scaffold(
        topBar = {
            KreedaTopBar(title = "Post Challenge", onBackClick = onNavigateBack)
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
                text = "Challenge another team! 🔥",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = teamName,
                onValueChange = { teamName = it },
                label = { Text("Your Team Name") },
                leadingIcon = { Icon(Icons.Outlined.Groups, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            // Sport selection
            Text("Sport", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sports.take(3).forEach { s ->
                    FilterChip(
                        selected = sport == s,
                        onClick = { sport = s },
                        label = { Text(s, style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                value = matchDate,
                onValueChange = { matchDate = it },
                label = { Text("Match Date") },
                leadingIcon = { Icon(Icons.Outlined.CalendarToday, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            // Ground selection
            Text("Ground", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                grounds.forEach { g ->
                    FilterChip(
                        selected = groundName == g,
                        onClick = { groundName = g },
                        label = { Text(g) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Challenge Description") },
                leadingIcon = { Icon(Icons.Outlined.Description, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                maxLines = 4
            )

            Button(
                onClick = {
                    viewModel.createChallenge(teamName, sport, matchDate, groundName, description)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = description.isNotEmpty() && groundName.isNotEmpty()
            ) {
                Icon(Icons.Filled.Campaign, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Post Challenge", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
