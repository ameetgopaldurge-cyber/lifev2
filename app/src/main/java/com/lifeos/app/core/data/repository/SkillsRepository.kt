package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.CodingProject
import com.lifeos.app.core.model.Course
import com.lifeos.app.core.model.ProjectStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SkillsRepository {
    fun getCourses(): Flow<List<Course>>
    fun getProjects(): Flow<List<CodingProject>>
    suspend fun advanceModule(courseId: String)
    suspend fun addProject(project: CodingProject)
}

class InMemorySkillsRepository : SkillsRepository {
    private val _courses = MutableStateFlow<List<Course>>(
        listOf(
            Course(id = "c_01", title = "React.js", completedModules = 4, totalModules = 11),
            Course(id = "c_02", title = "JavaScript", completedModules = 6, totalModules = 11),
            Course(id = "c_03", title = "Node.js", completedModules = 16, totalModules = 20),
            Course(id = "c_04", title = "SQL", completedModules = 10, totalModules = 10),
            Course(id = "c_05", title = "Python", completedModules = 20, totalModules = 20),
            Course(id = "c_06", title = "CSS & Tailwind", completedModules = 4, totalModules = 11)
        )
    )

    private val _projects = MutableStateFlow<List<CodingProject>>(
        listOf(
            CodingProject(
                id = "p_01",
                title = "LifeOS Native Android App",
                description = "Modern dark-themed operating system in Kotlin and Jetpack Compose.",
                techStack = listOf("Kotlin", "Compose", "Coroutines", "M3"),
                githubUrl = "https://github.com/alex/lifeos-android",
                status = ProjectStatus.MVP_READY,
                isPortfolioReady = true
            ),
            CodingProject(
                id = "p_02",
                title = "Realtime Supabase Sync Engine",
                description = "Offline-first sync repository architecture using Kotlin Flow.",
                techStack = listOf("Kotlin", "PostgreSQL", "Supabase"),
                githubUrl = "https://github.com/alex/supabase-sync-kt",
                status = ProjectStatus.IN_PROGRESS,
                isPortfolioReady = false
            )
        )
    )

    override fun getCourses(): Flow<List<Course>> = _courses
    override fun getProjects(): Flow<List<CodingProject>> = _projects

    override suspend fun advanceModule(courseId: String) {
        _courses.value = _courses.value.map {
            if (it.id == courseId && it.completedModules < it.totalModules) {
                it.copy(completedModules = it.completedModules + 1)
            } else it
        }
    }

    override suspend fun addProject(project: CodingProject) {
        _projects.value = listOf(project) + _projects.value
    }
}
