package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.Exercise
import com.lifeos.app.core.model.FitnessDayLog
import com.lifeos.app.core.model.WeightRecord
import com.lifeos.app.core.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface FitnessRepository {
    fun getFitnessLog(): Flow<FitnessDayLog>
    fun getActiveWorkout(): Flow<WorkoutPlan>
    fun getWeightHistory(): Flow<List<WeightRecord>>
    suspend fun addWater(ml: Int)
    suspend fun logWeight(kg: Double)
    suspend fun toggleExercise(id: String)
}

class InMemoryFitnessRepository : FitnessRepository {
    private val _fitnessLog = MutableStateFlow(FitnessDayLog())

    private val _workoutPlan = MutableStateFlow(
        WorkoutPlan(
            title = "Full Body Hypertrophy",
            targetCalories = 1000,
            burnedCalories = 918,
            exercises = listOf(
                Exercise(id = "ex_1", name = "Single-leg deadlifts", targetSets = 4, targetReps = 10, weightKg = 30.0),
                Exercise(id = "ex_2", name = "Squat", targetSets = 4, targetReps = 8, weightKg = 25.0),
                Exercise(id = "ex_3", name = "Treadmill", targetSets = 1, targetReps = 1, weightKg = 0.0, durationMinutes = 10),
                Exercise(id = "ex_4", name = "Dumbbell Press", targetSets = 3, targetReps = 12, weightKg = 15.0)
            )
        )
    )

    private val _weightHistory = MutableStateFlow(
        listOf(
            WeightRecord(weightKg = 54.8, dateText = "May 23"),
            WeightRecord(weightKg = 54.5, dateText = "May 24"),
            WeightRecord(weightKg = 54.2, dateText = "May 25"),
            WeightRecord(weightKg = 54.0, dateText = "May 26")
        )
    )

    override fun getFitnessLog(): Flow<FitnessDayLog> = _fitnessLog
    override fun getActiveWorkout(): Flow<WorkoutPlan> = _workoutPlan
    override fun getWeightHistory(): Flow<List<WeightRecord>> = _weightHistory

    override suspend fun addWater(ml: Int) {
        val current = _fitnessLog.value
        _fitnessLog.value = current.copy(waterIntakeMl = (current.waterIntakeMl + ml).coerceAtLeast(0))
    }

    override suspend fun logWeight(kg: Double) {
        val current = _fitnessLog.value
        _fitnessLog.value = current.copy(currentWeightKg = kg)
        _weightHistory.value = _weightHistory.value + WeightRecord(weightKg = kg, dateText = "Today")
    }

    override suspend fun toggleExercise(id: String) {
        val current = _workoutPlan.value
        val updated = current.exercises.map {
            if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
        }
        _workoutPlan.value = current.copy(exercises = updated)
    }
}
