package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.Goal
import com.lifeos.app.core.model.LifeArea
import com.lifeos.app.core.model.Milestone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface GoalRepository {
    fun getGoals(): Flow<List<Goal>>
    fun getGoalById(id: String): Flow<Goal?>
    suspend fun addGoal(goal: Goal)
    suspend fun toggleMilestone(goalId: String, milestoneId: String)
}

class InMemoryGoalRepository : GoalRepository {
    private val _goals = MutableStateFlow<List<Goal>>(
        listOf(
            Goal(
                id = "goal_01",
                title = "Launch LifeOS Native App",
                description = "Build and release a production-grade personal operating system app.",
                area = LifeArea.SKILLS,
                targetDate = "June 30",
                milestones = listOf(
                    Milestone(id = "m1", title = "Compose Theme & Components", isCompleted = true),
                    Milestone(id = "m2", title = "6 Life Areas Navigation & Screens", isCompleted = true),
                    Milestone(id = "m3", title = "Supabase Realtime Cloud Sync", isCompleted = false)
                )
            ),
            Goal(
                id = "goal_02",
                title = "Achieve 3.8+ GPA This Semester",
                description = "Maintain >85% attendance and submit all assignments 24h early.",
                area = LifeArea.COLLEGE,
                targetDate = "July 15",
                milestones = listOf(
                    Milestone(id = "m4", title = "Score >90% on Algebra Midterm", isCompleted = false),
                    Milestone(id = "m5", title = "Complete CS Capstone Project", isCompleted = true)
                )
            ),
            Goal(
                id = "goal_03",
                title = "Earn ₹2,00,000 from Freelance Engineering",
                description = "Scale client retainers and deploy high-value software projects.",
                area = LifeArea.INCOME,
                targetDate = "August 31",
                milestones = listOf(
                    Milestone(id = "m6", title = "Close 2 ongoing retainer clients", isCompleted = true),
                    Milestone(id = "m7", title = "Ship mobile MVP for HyperScale", isCompleted = false)
                )
            )
        )
    )

    override fun getGoals(): Flow<List<Goal>> = _goals

    override fun getGoalById(id: String): Flow<Goal?> =
        _goals.map { list -> list.find { it.id == id } }

    override suspend fun addGoal(goal: Goal) {
        _goals.value = listOf(goal) + _goals.value
    }

    override suspend fun toggleMilestone(goalId: String, milestoneId: String) {
        _goals.value = _goals.value.map { goal ->
            if (goal.id == goalId) {
                val updated = goal.milestones.map { m ->
                    if (m.id == milestoneId) m.copy(isCompleted = !m.isCompleted) else m
                }
                goal.copy(milestones = updated)
            } else goal
        }
    }
}
