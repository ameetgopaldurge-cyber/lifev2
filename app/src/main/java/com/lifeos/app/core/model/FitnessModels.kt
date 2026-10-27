package com.lifeos.app.core.model

import java.util.UUID

data class Exercise(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val targetSets: Int,
    val targetReps: Int,
    val weightKg: Double,
    val durationMinutes: Int = 0,
    val isCompleted: Boolean = false
)

data class WorkoutPlan(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val targetCalories: Int = 1000,
    val burnedCalories: Int = 918,
    val exercises: List<Exercise> = emptyList()
)

data class WeightRecord(
    val id: String = UUID.randomUUID().toString(),
    val weightKg: Double,
    val dateText: String
)

data class FitnessDayLog(
    val burnedCalories: Int = 918,
    val targetCalories: Int = 1000,
    val currentSteps: Int = 7125,
    val targetSteps: Int = 8000,
    val waterIntakeMl: Int = 2250,
    val targetWaterMl: Int = 3000,
    val sleepHours: Double = 7.5,
    val currentWeightKg: Double = 54.0
)
