package com.lifeos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lifeos.app.core.designsystem.theme.LifeOSBackground
import com.lifeos.app.core.designsystem.theme.LifeOSTheme
import com.lifeos.app.core.navigation.LifeOSBottomBar
import com.lifeos.app.core.navigation.LifeOSNavGraph
import com.lifeos.app.feature.analytics.AnalyticsViewModel
import com.lifeos.app.feature.college.CollegeViewModel
import com.lifeos.app.feature.creator.CreatorViewModel
import com.lifeos.app.feature.discipline.DisciplineViewModel
import com.lifeos.app.feature.fitness.FitnessViewModel
import com.lifeos.app.feature.goals.GoalsViewModel
import com.lifeos.app.feature.home.HomeViewModel
import com.lifeos.app.feature.income.IncomeViewModel
import com.lifeos.app.feature.planner.PlannerViewModel
import com.lifeos.app.feature.profile.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as LifeOSApplication

        val homeViewModel = HomeViewModel(app.taskRepository, app.goalRepository, app.userRepository)
        val plannerViewModel = PlannerViewModel(app.taskRepository, app.goalRepository)
        val collegeViewModel = CollegeViewModel(app.collegeRepository)
        val fitnessViewModel = FitnessViewModel(app.fitnessRepository)
        val creatorViewModel = CreatorViewModel(app.creatorRepository)
        val skillsViewModel = SkillsViewModel(app.skillsRepository)
        val incomeViewModel = IncomeViewModel(app.incomeRepository, app.userRepository)
        val disciplineViewModel = DisciplineViewModel(app.disciplineRepository)
        val goalsViewModel = GoalsViewModel(app.goalRepository)
        val analyticsViewModel = AnalyticsViewModel(app.taskRepository, app.collegeRepository, app.incomeRepository, app.disciplineRepository)
        val profileViewModel = ProfileViewModel(app.userRepository, app.authService)

        setContent {
            LifeOSTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = LifeOSBackground,
                    bottomBar = {
                        LifeOSBottomBar(navController = navController)
                    }
                ) { innerPadding ->
                    LifeOSNavGraph(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        plannerViewModel = plannerViewModel,
                        collegeViewModel = collegeViewModel,
                        fitnessViewModel = fitnessViewModel,
                        creatorViewModel = creatorViewModel,
                        skillsViewModel = skillsViewModel,
                        incomeViewModel = incomeViewModel,
                        disciplineViewModel = disciplineViewModel,
                        goalsViewModel = goalsViewModel,
                        analyticsViewModel = analyticsViewModel,
                        profileViewModel = profileViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
