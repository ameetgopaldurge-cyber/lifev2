package com.lifeos.app.core.model

import java.util.UUID

data class Habit(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val streakDays: Int,
    val isCompletedToday: Boolean,
    val bestStreak: Int = streakDays
)

data class RoutineItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isMorning: Boolean = true,
    val isCompleted: Boolean = false
)

data class FocusSessionRecord(
    val id: String = UUID.randomUUID().toString(),
    val taskTitle: String,
    val durationMinutes: Int,
    val dateText: String,
    val distractionCount: Int = 0
)

data class ReflectionEntry(
    val id: String = UUID.randomUUID().toString(),
    val dateText: String,
    val highlight: String,
    val frictionPoint: String,
    val tomorrowPriority: String
)
