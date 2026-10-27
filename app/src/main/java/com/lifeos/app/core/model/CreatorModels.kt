package com.lifeos.app.core.model

import java.util.UUID

enum class ProductionStage {
    DRAFT, EDITING, READY, SCHEDULED, PUBLISHED
}

data class ContentIdea(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val platform: String = "Instagram Reels",
    val tags: List<String> = emptyList(),
    val stage: ProductionStage = ProductionStage.DRAFT
)

data class VideoProject(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val platform: String = "Instagram Reels",
    val stage: ProductionStage,
    val durationText: String = "00:45",
    val fileSizeText: String = "2.3 MB",
    val script: String = "",
    val shotList: List<String> = emptyList(),
    val caption: String = "",
    val hashtags: String = "#lifeos #productivity #coding",
    val manualViews: Int = 0,
    val manualLikes: Int = 0
)

data class CreatorInsights(
    val reelViews: Int = 472206,
    val reach: Int = 333127,
    val netFollows: Int = 1520,
    val weeklyViews: List<Int> = listOf(45000, 68000, 92000, 85000, 110000, 72000, 64000)
)
