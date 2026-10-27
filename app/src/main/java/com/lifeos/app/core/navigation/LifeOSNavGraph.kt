package com.lifeos.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lifeos.app.feature.analytics.AnalyticsScreen
import com.lifeos.app.feature.analytics.AnalyticsViewModel
import com.lifeos.app.feature.college.AddAssignmentScreen
import com.lifeos.app.feature.college.CollegeDashboardScreen
import com.lifeos.app.feature.college.CollegeViewModel
import com.lifeos.app.feature.creator.AddIdeaScreen
import com.lifeos.app.feature.creator.CreatorStudioScreen
import com.lifeos.app.feature.creator.CreatorViewModel
import com.lifeos.app.feature.discipline.DisciplineHabitsScreen
import com.lifeos.app.feature.discipline.DisciplineViewModel
import com.lifeos.app.feature.fitness.ActiveWorkoutScreen
import com.lifeos.app.feature.fitness.FitnessDashboardScreen
import com.lifeos.app.feature.fitness.FitnessViewModel
import com.lifeos.app.feature.goals.AddGoalScreen
import com.lifeos.app.feature.goals.GoalsScreen
import com.lifeos.app.feature.goals.GoalsViewModel
import com.lifeos.app.feature.home.HomeDashboardScreen
import com.lifeos.app.feature.home.HomeViewModel
import com.lifeos.app.feature.income.AddClientScreen
import com.lifeos.app.feature.income.AddTransactionScreen
import com.lifeos.app.feature.income.IncomeFreelanceScreen
import com.lifeos.app.feature.income.IncomeViewModel
import com.lifeos.app.feature.lifeareas.LifeAreasHubScreen
import com.lifeos.app.feature.planner.AddEditTaskScreen
import com.lifeos.app.feature.planner.FocusSessionScreen
import com.lifeos.app.feature.planner.PlannerScreen
import com.lifeos.app.feature.planner.PlannerViewModel
import com.lifeos.app.feature.planner.TaskDetailScreen
import com.lifeos.app.feature.profile.ProfileScreen
import com.lifeos.app.feature.profile.ProfileViewModel
import com.lifeos.app.feature.profile.SettingsScreen
import com.lifeos.app.feature.profile.SupabaseAuthScreen
import com.lifeos.app.feature.skills.AddProjectScreen
import com.lifeos.app.feature.skills.SkillsDashboardScreen
import com.lifeos.app.feature.skills.SkillsViewModel

@Composable
fun LifeOSNavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    plannerViewModel: PlannerViewModel,
    collegeViewModel: CollegeViewModel,
    fitnessViewModel: FitnessViewModel,
    creatorViewModel: CreatorViewModel,
    skillsViewModel: SkillsViewModel,
    incomeViewModel: IncomeViewModel,
    disciplineViewModel: DisciplineViewModel,
    goalsViewModel: GoalsViewModel,
    analyticsViewModel: AnalyticsViewModel,
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Bottom Navigation destinations
        composable(Screen.Home.route) {
            HomeDashboardScreen(
                viewModel = homeViewModel,
                onNavigateToLifeAreas = { navController.navigate(Screen.LifeAreas.route) },
                onNavigateToLifeArea = { route -> navController.navigate(route) },
                onNavigateToAddTask = { navController.navigate(Screen.AddTask.route) },
                onNavigateToTaskDetail = { taskId -> navController.navigate(Screen.TaskDetail.createRoute(taskId)) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToAnalytics = { navController.navigate(Screen.Analytics.route) }
            )
        }

        composable(Screen.Planner.route) {
            PlannerScreen(
                viewModel = plannerViewModel,
                onNavigateToAddTask = { navController.navigate(Screen.AddTask.route) },
                onNavigateToTaskDetail = { taskId -> navController.navigate(Screen.TaskDetail.createRoute(taskId)) },
                onNavigateToFocusSession = { navController.navigate(Screen.FocusSession.route) }
            )
        }

        composable(Screen.Goals.route) {
            GoalsScreen(
                viewModel = goalsViewModel,
                onNavigateToAddGoal = { navController.navigate(Screen.AddGoal.route) }
            )
        }

        composable(Screen.Analytics.route) {
            AnalyticsScreen(
                viewModel = analyticsViewModel
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToAuth = { navController.navigate(Screen.SupabaseAuth.route) }
            )
        }

        // Life Areas Hub
        composable(Screen.LifeAreas.route) {
            LifeAreasHubScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToArea = { route -> navController.navigate(route) }
            )
        }

        // Domain Dashboards
        composable(Screen.College.route) {
            CollegeDashboardScreen(
                viewModel = collegeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddAssignment = { navController.navigate(Screen.AddAssignment.route) }
            )
        }

        composable(Screen.Fitness.route) {
            FitnessDashboardScreen(
                viewModel = fitnessViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToActiveWorkout = { navController.navigate(Screen.ActiveWorkout.route) }
            )
        }

        composable(Screen.Creator.route) {
            CreatorStudioScreen(
                viewModel = creatorViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddIdea = { navController.navigate(Screen.AddIdea.route) }
            )
        }

        composable(Screen.Skills.route) {
            SkillsDashboardScreen(
                viewModel = skillsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddProject = { navController.navigate(Screen.AddCodingProject.route) }
            )
        }

        composable(Screen.Income.route) {
            IncomeFreelanceScreen(
                viewModel = incomeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddTransaction = { navController.navigate(Screen.AddTransaction.route) },
                onNavigateToAddClient = { navController.navigate(Screen.AddClient.route) }
            )
        }

        composable(Screen.Discipline.route) {
            DisciplineHabitsScreen(
                viewModel = disciplineViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Actions & Modals
        composable(Screen.AddTask.route) {
            AddEditTaskScreen(
                viewModel = plannerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailScreen(
                taskId = taskId,
                viewModel = plannerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FocusSession.route) {
            FocusSessionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddAssignment.route) {
            AddAssignmentScreen(
                viewModel = collegeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ActiveWorkout.route) {
            ActiveWorkoutScreen(
                viewModel = fitnessViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddIdea.route) {
            AddIdeaScreen(
                viewModel = creatorViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddCodingProject.route) {
            AddProjectScreen(
                viewModel = skillsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                viewModel = incomeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddClient.route) {
            AddClientScreen(
                viewModel = incomeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddGoal.route) {
            AddGoalScreen(
                viewModel = goalsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = profileViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.SupabaseAuth.route) {
            SupabaseAuthScreen(
                viewModel = profileViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

