package com.lifeos.app.feature.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.CreatorRepository
import com.lifeos.app.core.model.ContentIdea
import com.lifeos.app.core.model.CreatorInsights
import com.lifeos.app.core.model.ProductionStage
import com.lifeos.app.core.model.VideoProject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CreatorUiState(
    val projects: List<VideoProject> = emptyList(),
    val ideas: List<ContentIdea> = emptyList(),
    val insights: CreatorInsights = CreatorInsights(),
    val selectedTab: Int = 0, // 0 = Insights, 1 = Projects, 2 = Ideas
    val isLoading: Boolean = false
)

class CreatorViewModel(
    private val creatorRepository: CreatorRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)

    val uiState: StateFlow<CreatorUiState> = combine(
        creatorRepository.getProjects(),
        creatorRepository.getIdeas(),
        creatorRepository.getInsights(),
        _selectedTab
    ) { projects, ideas, insights, tab ->
        CreatorUiState(
            projects = projects,
            ideas = ideas,
            insights = insights,
            selectedTab = tab,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CreatorUiState(isLoading = true)
    )

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    fun addIdea(title: String, platform: String, tags: List<String>) {
        viewModelScope.launch {
            creatorRepository.addIdea(ContentIdea(title = title, platform = platform, tags = tags))
        }
    }
}
