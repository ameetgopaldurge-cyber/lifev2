package com.lifeos.app.feature.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.SkillsRepository
import com.lifeos.app.core.model.CodingProject
import com.lifeos.app.core.model.Course
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SkillsUiState(
    val courses: List<Course> = emptyList(),
    val projects: List<CodingProject> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class SkillsViewModel(
    private val skillsRepository: SkillsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<SkillsUiState> = combine(
        skillsRepository.getCourses(),
        skillsRepository.getProjects(),
        _searchQuery
    ) { courses, projects, query ->
        val filtered = if (query.isBlank()) courses else courses.filter { it.title.contains(query, ignoreCase = true) }
        SkillsUiState(
            courses = filtered,
            projects = projects,
            searchQuery = query,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SkillsUiState(isLoading = true)
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun advanceCourse(courseId: String) {
        viewModelScope.launch {
            skillsRepository.advanceModule(courseId)
        }
    }

    fun addProject(project: CodingProject) {
        viewModelScope.launch {
            skillsRepository.addProject(project)
        }
    }
}
