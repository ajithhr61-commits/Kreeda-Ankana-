package com.kreeda.ankana.navigation

/**
 * Screen — Type-safe navigation route definitions for all screens.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object GroundList : Screen("ground_list")
    object GroundBooking : Screen("ground_booking/{groundId}") {
        fun createRoute(groundId: String) = "ground_booking/$groundId"
    }
    object Calendar : Screen("calendar")
    object ChallengeBoard : Screen("challenge_board")
    object CreateChallenge : Screen("create_challenge")
    object TeamList : Screen("team_list")
    object TeamDetail : Screen("team_detail/{teamId}") {
        fun createRoute(teamId: String) = "team_detail/$teamId"
    }
    object CreateTeam : Screen("create_team")
    object ScoreWall : Screen("score_wall")
    object PostScore : Screen("post_score")
    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
    object EditProfile : Screen("edit_profile")
}
