package com.kreeda.ankana.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kreeda.ankana.data.model.User
import com.kreeda.ankana.ui.components.KreedaTopBar
import com.kreeda.ankana.viewmodel.SettingsViewModel

@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit,
    onProfileSaved: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val user by viewModel.currentUser.collectAsState()

    var name by remember(user) { mutableStateOf(user?.name ?: "") }
    var email by remember(user) { mutableStateOf(user?.email ?: "") }
    var phone by remember(user) { mutableStateOf(user?.phone ?: "") }
    var village by remember(user) { mutableStateOf(user?.village ?: "") }
    var sportsType by remember(user) { mutableStateOf(user?.sportsType ?: "") }
    var teamName by remember(user) { mutableStateOf(user?.teamName ?: "") }

    Scaffold(
        topBar = { KreedaTopBar(title = "Edit Profile", onBackClick = onNavigateBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Update your athlete profile", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Full Name") },
                leadingIcon = { Icon(Icons.Outlined.Person, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), singleLine = true
            )
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Outlined.Email, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), singleLine = true
            )
            OutlinedTextField(
                value = phone, onValueChange = { phone = it },
                label = { Text("Phone") },
                leadingIcon = { Icon(Icons.Outlined.Phone, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), singleLine = true
            )
            OutlinedTextField(
                value = village, onValueChange = { village = it },
                label = { Text("Village / Town") },
                leadingIcon = { Icon(Icons.Outlined.LocationOn, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), singleLine = true
            )
            OutlinedTextField(
                value = sportsType, onValueChange = { sportsType = it },
                label = { Text("Primary Sport") },
                leadingIcon = { Icon(Icons.Outlined.SportsCricket, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), singleLine = true
            )
            OutlinedTextField(
                value = teamName, onValueChange = { teamName = it },
                label = { Text("Team Name") },
                leadingIcon = { Icon(Icons.Outlined.Groups, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    user?.let { u ->
                        viewModel.updateProfile(
                            u.copy(
                                name = name, email = email, phone = phone,
                                village = village, sportsType = sportsType, teamName = teamName
                            )
                        )
                    }
                    onProfileSaved()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Save Profile", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
