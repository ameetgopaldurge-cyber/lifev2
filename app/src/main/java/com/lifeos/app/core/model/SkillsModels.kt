package com.lifeos.app.core.model

import java.util.UUID

data class Course(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val completedModules: Int,
    val totalModules: Int,
    val iconKey: String = "code"
) {
    val completionPercentage: Int
        get() = if (totalModules > 0) ((completedModules.toDouble() / totalModules) * 100).toInt() else 0

    val isCompleted: Boolean
        get() = completedModules >= totalModules
}

enum class ProjectStatus {
    CONCEPT, IN_PROGRESS, MVP_READY, DEPLOYED
}

data class CodingProject(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val techStack: List<String>,
    val githubUrl: String = "",
    val status: ProjectStatus = ProjectStatus.IN_PROGRESS,
    val isPortfolioReady: Boolean = false
)
