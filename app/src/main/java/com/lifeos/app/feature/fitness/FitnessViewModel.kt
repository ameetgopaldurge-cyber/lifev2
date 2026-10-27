package com.lifeos.app.feature.fitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.FitnessRepository
import com.lifeos.app.core.model.FitnessDayLog
import com.lifeos.app.core.model.WeightRecord
import com.lifeos.app.core.model.WorkoutPlan
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class FitnessUiState(
    val dayLog: FitnessDayLog = FitnessDayLog(),
    val workoutPlan: WorkoutPlan = WorkoutPlan(),
    val weightHistory: List<WeightRecord> = emptyList(),
    val isLoading: Boolean = false
)

class FitnessViewModel(
    private val fitnessRepository: FitnessRepository
) : ViewModel() {

    val uiState: StateFlow<FitnessUiState> = combine(
        fitnessRepository.getFitnessLog(),
        fitnessRepository.getActiveWorkout(),
        fitnessRepository.getWeightHistory()
    ) { log, workout, weights ->
        FitnessUiState(
            dayLog = log,
            workoutPlan = workout,
            weightHistory = weights,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FitnessUiState(isLoading = true)
    )

    fun addWater(ml: Int) {
        viewModelScope.launch {
            fitnessRepository.addWater(ml)
        }
    }

    fun logWeight(kg: Double) {
        viewModelScope.launch {
            fitnessRepository.logWeight(kg)
        }
    }

    fun toggleExercise(exerciseId: String) {
        viewModelScope.launch {
            fitnessRepository.toggleExercise(exerciseId)
        }
    }
}
