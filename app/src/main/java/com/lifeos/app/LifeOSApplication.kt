package com.lifeos.app

import android.app.Application
import com.lifeos.app.core.data.repository.*
import com.lifeos.app.core.data.supabase.SupabaseAuthService

class LifeOSApplication : Application() {

    // Repositories instantiated as reactive singletons
    val taskRepository: TaskRepository by lazy { InMemoryTaskRepository() }
    val collegeRepository: CollegeRepository by lazy { InMemoryCollegeRepository() }
    val fitnessRepository: FitnessRepository by lazy { InMemoryFitnessRepository() }
    val creatorRepository: CreatorRepository by lazy { InMemoryCreatorRepository() }
    val skillsRepository: SkillsRepository by lazy { InMemorySkillsRepository() }
    val incomeRepository: IncomeRepository by lazy { InMemoryIncomeRepository() }
    val disciplineRepository: DisciplineRepository by lazy { InMemoryDisciplineRepository() }
    val goalRepository: GoalRepository by lazy { InMemoryGoalRepository() }
    val userRepository: UserRepository by lazy { InMemoryUserRepository() }
    val authService: SupabaseAuthService by lazy { SupabaseAuthService() }

    override fun onCreate() {
        super.onCreate()
    }
}
