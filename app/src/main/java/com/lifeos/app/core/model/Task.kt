package com.lifeos.app.core.model

import java.util.UUID

enum class TaskPriority {
    HIGH, MEDIUM, LOW
}

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: String = "Today",
    val dueTime: String = "18:00",
    val isCompleted: Boolean = false,
    val linkedArea: LifeArea? = null,
    val linkedGoalId: String? = null,
    val isOverdue: Boolean = false,
    val recurringRule: String = "None" // "None", "Daily", "Weekly"
)
