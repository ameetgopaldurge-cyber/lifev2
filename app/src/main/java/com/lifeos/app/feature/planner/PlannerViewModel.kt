package com.lifeos.app.feature.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.GoalRepository
import com.lifeos.app.core.data.repository.TaskRepository
import com.lifeos.app.core.model.Goal
import com.lifeos.app.core.model.LifeArea
import com.lifeos.app.core.model.Task
import com.lifeos.app.core.model.TaskPriority
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PlannerUiState(
    val tasks: List<Task> = emptyList(),
    val filteredTasks: List<Task> = emptyList(),
    val goals: List<Goal> = emptyList(),
    val selectedFilter: String = "All", // "All", "High", "Medium", "Low", "Overdue", "Completed"
    val selectedDate: String = "Today",
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class PlannerViewModel(
    private val taskRepository: TaskRepository,
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow("All")
    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<PlannerUiState> = combine(
        taskRepository.getTasks(),
        goalRepository.getGoals(),
        _selectedFilter,
        _searchQuery
    ) { tasks, goals, filter, query ->
        val filtered = tasks.filter { task ->
            val matchesFilter = when (filter) {
                "High" -> task.priority == TaskPriority.HIGH
                "Medium" -> task.priority == TaskPriority.MEDIUM
                "Low" -> task.priority == TaskPriority.LOW
                "Overdue" -> task.isOverdue || task.dueDate.contains("Past Due")
                "Completed" -> task.isCompleted
                else -> true
            }
            val matchesSearch = query.isBlank() || task.title.contains(query, ignoreCase = true) || task.description.contains(query, ignoreCase = true)
            matchesFilter && matchesSearch
        }

        PlannerUiState(
            tasks = tasks,
            filteredTasks = filtered,
            goals = goals,
            selectedFilter = filter,
            searchQuery = query,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlannerUiState(isLoading = true)
    )

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.toggleTaskCompletion(taskId)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }

    fun addTask(
        title: String,
        description: String,
        priority: TaskPriority,
        dueDate: String,
        dueTime: String,
        linkedArea: LifeArea?,
        linkedGoalId: String?,
        recurringRule: String
    ) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description,
                priority = priority,
                dueDate = dueDate,
                dueTime = dueTime,
                linkedArea = linkedArea,
                linkedGoalId = linkedGoalId,
                recurringRule = recurringRule
            )
            taskRepository.addTask(task)
        }
    }
}
