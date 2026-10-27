package com.lifeos.app.feature.college

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.CollegeRepository
import com.lifeos.app.core.model.Assignment
import com.lifeos.app.core.model.AssignmentStatus
import com.lifeos.app.core.model.ExamCountdown
import com.lifeos.app.core.model.Subject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CollegeUiState(
    val subjects: List<Subject> = emptyList(),
    val assignments: List<Assignment> = emptyList(),
    val exams: List<ExamCountdown> = emptyList(),
    val averageAttendance: Double = 0.0,
    val isLoading: Boolean = false
)

class CollegeViewModel(
    private val collegeRepository: CollegeRepository
) : ViewModel() {

    val uiState: StateFlow<CollegeUiState> = combine(
        collegeRepository.getSubjects(),
        collegeRepository.getAssignments(),
        collegeRepository.getUpcomingExams()
    ) { subjects, assignments, exams ->
        val totalAttended = subjects.sumOf { it.attendedClasses }
        val totalClasses = subjects.sumOf { it.totalClasses }
        val avg = if (totalClasses > 0) (totalAttended.toDouble() / totalClasses) * 100.0 else 0.0

        CollegeUiState(
            subjects = subjects,
            assignments = assignments,
            exams = exams,
            averageAttendance = avg,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CollegeUiState(isLoading = true)
    )

    fun recordAttendance(subjectId: String, attended: Boolean) {
        viewModelScope.launch {
            collegeRepository.recordAttendance(subjectId, attended)
        }
    }

    fun toggleAssignmentSubmission(assignmentId: String) {
        viewModelScope.launch {
            collegeRepository.toggleAssignmentSubmission(assignmentId)
        }
    }

    fun addAssignment(
        subjectId: String,
        subjectName: String,
        title: String,
        dueDateText: String,
        dueTimeText: String,
        categoryBadge: String
    ) {
        viewModelScope.launch {
            val assignment = Assignment(
                subjectId = subjectId,
                subjectName = subjectName,
                title = title,
                dueDateText = dueDateText,
                dueTimeText = dueTimeText,
                status = AssignmentStatus.UPCOMING,
                categoryBadge = categoryBadge
            )
            collegeRepository.addAssignment(assignment)
        }
    }
}
