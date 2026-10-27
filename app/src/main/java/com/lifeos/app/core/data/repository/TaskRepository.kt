package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.LifeArea
import com.lifeos.app.core.model.Task
import com.lifeos.app.core.model.TaskPriority
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    fun getTaskById(id: String): Flow<Task?>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: String)
    suspend fun toggleTaskCompletion(id: String)
}

class InMemoryTaskRepository : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(
        listOf(
            Task(
                id = "task_01",
                title = "Design UI Mockups",
                description = "Complete the high-fidelity native Compose components for LifeOS dark theme.",
                priority = TaskPriority.HIGH,
                dueDate = "Due Today",
                dueTime = "17:00",
                isCompleted = false,
                linkedArea = LifeArea.CREATOR
            ),
            Task(
                id = "task_02",
                title = "Review API Docs",
                description = "Review Supabase Kotlin SDK authentication and real-time channels.",
                priority = TaskPriority.MEDIUM,
                dueDate = "Due Tomorrow",
                dueTime = "14:00",
                isCompleted = false,
                linkedArea = LifeArea.SKILLS
            ),
            Task(
                id = "task_03",
                title = "Complete Biology Microscopy Report",
                description = "Summarize findings from lab specimen analysis.",
                priority = TaskPriority.HIGH,
                dueDate = "Due Today",
                dueTime = "18:00",
                isCompleted = false,
                linkedArea = LifeArea.COLLEGE
            ),
            Task(
                id = "task_04",
                title = "Send Freelance Invoices",
                description = "Issue milestone invoices for UX audit project.",
                priority = TaskPriority.MEDIUM,
                dueDate = "May 25",
                dueTime = "11:00",
                isCompleted = true,
                linkedArea = LifeArea.INCOME
            ),
            Task(
                id = "task_05",
                title = "Leg Day Workout Routine",
                description = "Single-leg deadlifts, squats and 10 mins treadmill.",
                priority = TaskPriority.LOW,
                dueDate = "Due Today",
                dueTime = "20:00",
                isCompleted = true,
                linkedArea = LifeArea.FITNESS
            )
        )
    )

    override fun getTasks(): Flow<List<Task>> = _tasks

    override fun getTaskById(id: String): Flow<Task?> =
        _tasks.map { list -> list.find { it.id == id } }

    override suspend fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    override suspend fun updateTask(task: Task) {
        _tasks.value = _tasks.value.map { if (it.id == task.id) task else it }
    }

    override suspend fun deleteTask(id: String) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }

    override suspend fun toggleTaskCompletion(id: String) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
        }
    }
}
