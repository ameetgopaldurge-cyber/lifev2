package com.lifeos.app.feature.discipline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.DisciplineRepository
import com.lifeos.app.core.model.Habit
import com.lifeos.app.core.model.ReflectionEntry
import com.lifeos.app.core.model.RoutineItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DisciplineUiState(
    val habits: List<Habit> = emptyList(),
    val routines: List<RoutineItem> = emptyList(),
    val reflections: List<ReflectionEntry> = emptyList(),
    val isLoading: Boolean = false
)

class DisciplineViewModel(
    private val disciplineRepository: DisciplineRepository
) : ViewModel() {

    val uiState: StateFlow<DisciplineUiState> = combine(
        disciplineRepository.getHabits(),
        disciplineRepository.getRoutines(),
        disciplineRepository.getReflections()
    ) { habits, routines, reflections ->
        DisciplineUiState(
            habits = habits,
            routines = routines,
            reflections = reflections,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DisciplineUiState(isLoading = true)
    )

    fun toggleHabit(habitId: String) {
        viewModelScope.launch {
            disciplineRepository.toggleHabit(habitId)
        }
    }

    fun toggleRoutine(routineId: String) {
        viewModelScope.launch {
            disciplineRepository.toggleRoutine(routineId)
        }
    }

    fun addReflection(highlight: String, friction: String, priority: String) {
        viewModelScope.launch {
            disciplineRepository.addReflection(
                ReflectionEntry(
                    dateText = "Today",
                    highlight = highlight,
                    frictionPoint = friction,
                    tomorrowPriority = priority
                )
            )
        }
    }
}
