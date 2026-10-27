package com.lifeos.app.core.model

import java.util.UUID

data class Subject(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val code: String,
    val professor: String,
    val attendedClasses: Int,
    val totalClasses: Int,
    val targetAttendancePercent: Double = 75.0,
    val badgeLetter: String = name.take(1)
) {
    val attendancePercentage: Double
        get() = if (totalClasses > 0) {
            (attendedClasses.toDouble() / totalClasses) * 100.0
        } else 0.0

    val isBelowThreshold: Boolean
        get() = attendancePercentage < targetAttendancePercent
}

enum class AssignmentStatus {
    PAST_DUE, TODAY, UPCOMING, COMPLETED
}

data class Assignment(
    val id: String = UUID.randomUUID().toString(),
    val subjectId: String,
    val subjectName: String,
    val title: String,
    val dueDateText: String,
    val dueTimeText: String,
    val status: AssignmentStatus,
    val categoryBadge: String = "Assignment", // "Presentations", "Lab Reports", "Reading Checks"
    val isSubmitted: Boolean = false
)

data class ExamCountdown(
    val id: String = UUID.randomUUID().toString(),
    val subjectName: String,
    val examTitle: String,
    val examDate: String,
    val daysRemaining: Int
)
