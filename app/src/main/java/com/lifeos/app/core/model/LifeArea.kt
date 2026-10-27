package com.lifeos.app.core.model

import androidx.compose.ui.graphics.Color
import com.lifeos.app.core.designsystem.theme.*

enum class LifeArea(
    val title: String,
    val subtitle: String,
    val route: String,
    val color: Color
) {
    COLLEGE(
        title = "College & Studies",
        subtitle = "Academics, attendance, exams & notes",
        route = "college_dashboard",
        color = LifeOSCollegeColor
    ),
    FITNESS(
        title = "Health & Fitness",
        subtitle = "Workouts, nutrition, sleep & water",
        route = "fitness_dashboard",
        color = LifeOSFitnessColor
    ),
    CREATOR(
        title = "Creator Studio",
        subtitle = "Video projects, scripts & ideas",
        route = "creator_studio",
        color = LifeOSCreatorColor
    ),
    SKILLS(
        title = "Skills & Projects",
        subtitle = "Learning roadmaps & coding projects",
        route = "skills_dashboard",
        color = LifeOSSkillsColor
    ),
    INCOME(
        title = "Income & Freelancing",
        subtitle = "Earnings, clients, expenses & pipeline",
        route = "income_freelance",
        color = LifeOSIncomeColor
    ),
    DISCIPLINE(
        title = "Discipline & Habits",
        subtitle = "Habits, streaks, focus & reflection",
        route = "discipline_habits",
        color = LifeOSDisciplineColor
    )
}
