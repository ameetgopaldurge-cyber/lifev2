package com.lifeos.app.core.navigation

sealed class Screen(val route: String) {
    // 5 Bottom Destinations
    object Home : Screen("home")
    object Planner : Screen("planner")
    object Goals : Screen("goals")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")

    // Life Areas Hub
    object LifeAreas : Screen("life_areas")

    // 6 Dedicated Domain Dashboards
    object College : Screen("college_dashboard")
    object Fitness : Screen("fitness_dashboard")
    object Creator : Screen("creator_studio")
    object Skills : Screen("skills_dashboard")
    object Income : Screen("income_freelance")
    object Discipline : Screen("discipline_habits")

    // Task & Planner Actions
    object AddTask : Screen("add_task")
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    object FocusSession : Screen("focus_session")

    // College Actions
    object AddAssignment : Screen("add_assignment")

    // Fitness Actions
    object ActiveWorkout : Screen("active_workout")

    // Creator Actions
    object AddIdea : Screen("add_idea")

    // Skills Actions
    object AddCodingProject : Screen("add_coding_project")

    // Income Actions
    object AddTransaction : Screen("add_transaction")
    object AddClient : Screen("add_client")

    // Goals Actions
    object AddGoal : Screen("add_goal")

    // Profile & Settings
    object Settings : Screen("settings")
    object SupabaseAuth : Screen("supabase_auth")
}
