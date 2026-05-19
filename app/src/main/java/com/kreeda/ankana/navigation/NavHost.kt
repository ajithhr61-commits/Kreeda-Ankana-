package com.kreeda.ankana.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kreeda.ankana.ui.screens.*

/**
 * KreedaNavHost — Central navigation graph for all app screens.
 * Uses animated transitions for a polished user experience.
 */
@Composable
fun KreedaNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { it / 4 } },
        exitTransition = { fadeOut(tween(200)) },
        popEnterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { -it / 4 } },
        popExitTransition = { fadeOut(tween(200)) + slideOutHorizontally(tween(200)) { it / 4 } }
    ) {
        // Splash
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Login
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Register
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Dashboard (main hub)
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToBooking = { navController.navigate(Screen.GroundList.route) },
                onNavigateToCalendar = { navController.navigate(Screen.Calendar.route) },
                onNavigateToChallenges = { navController.navigate(Screen.ChallengeBoard.route) },
                onNavigateToTeams = { navController.navigate(Screen.TeamList.route) },
                onNavigateToScoreWall = { navController.navigate(Screen.ScoreWall.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToPostScore = { navController.navigate(Screen.PostScore.route) }
            )
        }

        // Ground List
        composable(Screen.GroundList.route) {
            GroundListScreen(
                onNavigateBack = { navController.popBackStack() },
                onGroundSelected = { groundId ->
                    navController.navigate(Screen.GroundBooking.createRoute(groundId))
                }
            )
        }

        // Ground Booking
        composable(
            route = Screen.GroundBooking.route,
            arguments = listOf(navArgument("groundId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groundId = backStackEntry.arguments?.getString("groundId") ?: ""
            GroundBookingScreen(
                groundId = groundId,
                onNavigateBack = { navController.popBackStack() },
                onBookingConfirmed = {
                    navController.popBackStack(Screen.Dashboard.route, false)
                }
            )
        }

        // Calendar
        composable(Screen.Calendar.route) {
            CalendarScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Challenge Board
        composable(Screen.ChallengeBoard.route) {
            ChallengeBoardScreen(
                onNavigateBack = { navController.popBackStack() },
                onCreateChallenge = { navController.navigate(Screen.CreateChallenge.route) }
            )
        }

        // Create Challenge
        composable(Screen.CreateChallenge.route) {
            CreateChallengeScreen(
                onNavigateBack = { navController.popBackStack() },
                onChallengeCreated = { navController.popBackStack() }
            )
        }

        // Team List
        composable(Screen.TeamList.route) {
            TeamListScreen(
                onNavigateBack = { navController.popBackStack() },
                onTeamSelected = { teamId ->
                    navController.navigate(Screen.TeamDetail.createRoute(teamId))
                },
                onCreateTeam = { navController.navigate(Screen.CreateTeam.route) }
            )
        }

        // Team Detail
        composable(
            route = Screen.TeamDetail.route,
            arguments = listOf(navArgument("teamId") { type = NavType.StringType })
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId") ?: ""
            TeamDetailScreen(
                teamId = teamId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Create Team
        composable(Screen.CreateTeam.route) {
            CreateTeamScreen(
                onNavigateBack = { navController.popBackStack() },
                onTeamCreated = { navController.popBackStack() }
            )
        }

        // Score Wall
        composable(Screen.ScoreWall.route) {
            ScoreWallScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostScore = { navController.navigate(Screen.PostScore.route) }
            )
        }

        // Post Score
        composable(Screen.PostScore.route) {
            PostScoreScreen(
                onNavigateBack = { navController.popBackStack() },
                onScorePosted = { navController.popBackStack() }
            )
        }

        // Notifications
        composable(Screen.Notifications.route) {
            NotificationsScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Settings
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(Screen.EditProfile.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Edit Profile
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onProfileSaved = { navController.popBackStack() }
            )
        }
    }
}
