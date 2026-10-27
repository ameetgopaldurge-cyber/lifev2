package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.ContentIdea
import com.lifeos.app.core.model.CreatorInsights
import com.lifeos.app.core.model.ProductionStage
import com.lifeos.app.core.model.VideoProject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface CreatorRepository {
    fun getProjects(): Flow<List<VideoProject>>
    fun getIdeas(): Flow<List<ContentIdea>>
    fun getInsights(): Flow<CreatorInsights>
    suspend fun addIdea(idea: ContentIdea)
    suspend fun addProject(project: VideoProject)
    suspend fun updateProject(project: VideoProject)
}

class InMemoryCreatorRepository : CreatorRepository {
    private val _projects = MutableStateFlow<List<VideoProject>>(
        listOf(
            VideoProject(id = "vp_01", title = "Free throw challenge", stage = ProductionStage.EDITING, durationText = "00:30", fileSizeText = "2.3 MB", script = "Intro hook: Don't miss this shot!\nCut to slow motion."),
            VideoProject(id = "vp_02", title = "Hype squad routine", stage = ProductionStage.READY, durationText = "00:45", fileSizeText = "3.1 MB", caption = "Energy was unmatched today! 🔥"),
            VideoProject(id = "vp_03", title = "Touch grass vlog", stage = ProductionStage.DRAFT, durationText = "01:00", fileSizeText = "4.2 MB", script = "Walking through nature, explaining why taking breaks improves code quality."),
            VideoProject(id = "vp_04", title = "Sparky gets the zoomies", stage = ProductionStage.PUBLISHED, manualViews = 49200, manualLikes = 1300)
        )
    )

    private val _ideas = MutableStateFlow<List<ContentIdea>>(
        listOf(
            ContentIdea(title = "Day in life of a CS student + freelancer", platform = "YouTube Shorts", tags = listOf("Vlog", "Study")),
            ContentIdea(title = "3 Jetpack Compose UI tips no one talks about", platform = "Instagram Reels", tags = listOf("Android", "Dev")),
            ContentIdea(title = "How I organize 6 life areas in LifeOS", platform = "Instagram Reels", tags = listOf("LifeOS", "Productivity"))
        )
    )

    private val _insights = MutableStateFlow(CreatorInsights())

    override fun getProjects(): Flow<List<VideoProject>> = _projects
    override fun getIdeas(): Flow<List<ContentIdea>> = _ideas
    override fun getInsights(): Flow<CreatorInsights> = _insights

    override suspend fun addIdea(idea: ContentIdea) {
        _ideas.value = listOf(idea) + _ideas.value
    }

    override suspend fun addProject(project: VideoProject) {
        _projects.value = listOf(project) + _projects.value
    }

    override suspend fun updateProject(project: VideoProject) {
        _projects.value = _projects.value.map { if (it.id == project.id) project else it }
    }
}
