package com.lifeos.app.core.model

import java.util.UUID

data class Milestone(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isCompleted: Boolean = false
)

data class Goal(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val area: LifeArea,
    val targetDate: String,
    val milestones: List<Milestone> = emptyList(),
    val linkedTaskIds: List<String> = emptyList()
) {
    val progressPercentage: Int
        get() = if (milestones.isNotEmpty()) {
            val completed = milestones.count { it.isCompleted }
            ((completed.toDouble() / milestones.size) * 100).toInt()
        } else 0
}
