package com.kreeda.ankana.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kreeda.ankana.data.model.Ground
import com.kreeda.ankana.ui.components.*
import com.kreeda.ankana.ui.theme.*
import com.kreeda.ankana.viewmodel.GroundViewModel

/**
 * GroundListScreen — Shows all available sports grounds.
 */
@Composable
fun GroundListScreen(
    onNavigateBack: () -> Unit,
    onGroundSelected: (String) -> Unit,
    viewModel: GroundViewModel = hiltViewModel()
) {
    val grounds by viewModel.grounds.collectAsState()

    Scaffold(
        topBar = {
            KreedaTopBar(title = "Sports Grounds", onBackClick = onNavigateBack)
        }
    ) { padding ->
        if (grounds.isEmpty()) {
            EmptyStateView(
                icon = Icons.Outlined.Stadium,
                title = "No grounds available",
                subtitle = "Grounds will appear here once added",
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
                items(grounds) { ground ->
                    GroundCard(
                        ground = ground,
                        onClick = { onGroundSelected(ground.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun GroundCard(ground: Ground, onClick: () -> Unit) {
    val gradientColors = when {
        ground.sportTypes.contains("Cricket") -> listOf(Orange60, Orange40)
        ground.sportTypes.contains("Football") -> listOf(Teal50, Teal40)
        else -> listOf(Gold60, Gold40)
    }

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Gradient header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Brush.horizontalGradient(gradientColors)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Stadium,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White.copy(alpha = 0.4f)
                )
                // Availability badge
                if (ground.isAvailable) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = LiveGreen.copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = "● Available",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = ground.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = ground.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${ground.openTime} – ${ground.closeTime}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sport chips
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ground.sportTypes.split(",").forEach { sport ->
                        SportChip(sport = sport.trim())
                    }
                }
            }
        }
    }
}

/**
 * GroundBookingScreen — Book a specific ground with date/time selection.
 */
@Composable
fun GroundBookingScreen(
    groundId: String,
    onNavigateBack: () -> Unit,
    onBookingConfirmed: () -> Unit,
    viewModel: GroundViewModel = hiltViewModel()
) {
    val ground by viewModel.selectedGround.collectAsState()
    val existingBookings by viewModel.existingBookings.collectAsState()

    var selectedDate by remember { mutableStateOf("2024-03-25") }
    var selectedStartTime by remember { mutableStateOf("") }
    var selectedEndTime by remember { mutableStateOf("") }
    var selectedSport by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("Shivpur Warriors") }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val timeSlots = listOf(
        "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
        "18:00", "19:00", "20:00"
    )

    val dates = listOf(
        "2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23",
        "2024-03-24", "2024-03-25", "2024-03-26", "2024-03-27"
    )

    LaunchedEffect(groundId) {
        viewModel.loadGround(groundId)
    }

    LaunchedEffect(groundId, selectedDate) {
        viewModel.loadBookingsForDate(groundId, selectedDate)
    }

    // Collect booking success
    LaunchedEffect(Unit) {
        viewModel.bookingSuccess.collect { success ->
            if (success) onBookingConfirmed()
        }
    }

    Scaffold(
        topBar = {
            KreedaTopBar(title = "Book Ground", onBackClick = onNavigateBack)
        }
    ) { padding ->
        if (ground == null) {
            LoadingScreen()
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ground info header
            item {
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(listOf(Orange60, Teal50))
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Text(
                                text = ground!!.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = ground!!.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Date selection
            item {
                Text(
                    text = "Select Date",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(dates) { date ->
                        val isSelected = date == selectedDate
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedDate = date },
                            label = {
                                Text(
                                    date.takeLast(5), // show MM-DD
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        )
                    }
                }
            }

            // Sport selection
            item {
                Text(
                    text = "Select Sport",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ground!!.sportTypes.split(",").forEach { sport ->
                        val trimmed = sport.trim()
                        FilterChip(
                            selected = selectedSport == trimmed,
                            onClick = { selectedSport = trimmed },
                            label = { Text(trimmed) }
                        )
                    }
                }
            }

            // Time slot selection
            item {
                Text(
                    text = "Select Start Time",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Show time slots in rows of 4
                val rows = timeSlots.chunked(4)
                rows.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { time ->
                            val isBooked = existingBookings.any { b ->
                                b.startTime <= time && b.endTime > time
                            }
                            val isSelected = time == selectedStartTime
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    if (!isBooked) selectedStartTime = time
                                },
                                label = {
                                    Text(
                                        time,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                enabled = !isBooked,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // Fill remaining space if row is not full
                        repeat(4 - row.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // End time selection
            item {
                Text(
                    text = "Select End Time",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                val endTimeSlots = timeSlots.filter { it > selectedStartTime }
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(endTimeSlots.take(6)) { time ->
                        FilterChip(
                            selected = time == selectedEndTime,
                            onClick = { selectedEndTime = time },
                            label = { Text(time) }
                        )
                    }
                }
            }

            // Team name
            item {
                OutlinedTextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text("Team Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Outlined.Groups, null) }
                )
            }

            // Book button
            item {
                Button(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = selectedDate.isNotEmpty() && selectedStartTime.isNotEmpty()
                            && selectedEndTime.isNotEmpty() && selectedSport.isNotEmpty()
                ) {
                    Icon(Icons.Filled.CheckCircle, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Confirm Booking", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirm Booking", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("📍 ${ground?.name}")
                    Text("📅 $selectedDate")
                    Text("🕐 $selectedStartTime – $selectedEndTime")
                    Text("🏏 $selectedSport")
                    Text("👥 $teamName")
                }
            },
            confirmButton = {
                Button(onClick = {
                    showConfirmDialog = false
                    viewModel.createBooking(
                        groundId = groundId,
                        groundName = ground?.name ?: "",
                        sport = selectedSport,
                        date = selectedDate,
                        startTime = selectedStartTime,
                        endTime = selectedEndTime,
                        teamName = teamName
                    )
                }) {
                    Text("Book Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
