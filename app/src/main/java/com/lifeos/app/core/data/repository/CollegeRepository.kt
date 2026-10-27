package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.Assignment
import com.lifeos.app.core.model.AssignmentStatus
import com.lifeos.app.core.model.ExamCountdown
import com.lifeos.app.core.model.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface CollegeRepository {
    fun getSubjects(): Flow<List<Subject>>
    fun getSubjectById(id: String): Flow<Subject?>
    fun getAssignments(): Flow<List<Assignment>>
    fun getUpcomingExams(): Flow<List<ExamCountdown>>
    suspend fun recordAttendance(subjectId: String, attended: Boolean)
    suspend fun addAssignment(assignment: Assignment)
    suspend fun toggleAssignmentSubmission(id: String)
}

class InMemoryCollegeRepository : CollegeRepository {
    private val _subjects = MutableStateFlow<List<Subject>>(
        listOf(
            Subject(id = "sub_01", name = "Algebra II", code = "MATH201", professor = "Dr. Miller", attendedClasses = 31, totalClasses = 35),
            Subject(id = "sub_02", name = "Biology", code = "BIO104", professor = "Prof. Chen", attendedClasses = 22, totalClasses = 30),
            Subject(id = "sub_03", name = "History", code = "HIST110", professor = "Dr. Vance", attendedClasses = 28, totalClasses = 30),
            Subject(id = "sub_04", name = "Intro to Programming", code = "CS101", professor = "Dr. Adams", attendedClasses = 32, totalClasses = 35),
            Subject(id = "sub_05", name = "Studio Art", code = "ART150", professor = "Ms. Ross", attendedClasses = 26, totalClasses = 31)
        )
    )

    private val _assignments = MutableStateFlow<List<Assignment>>(
        listOf(
            Assignment(id = "asg_01", subjectId = "sub_03", subjectName = "History", title = "Presentation Slides: Modern Europe", dueDateText = "May 14 · 1 week ago", dueTimeText = "11:59 pm", status = AssignmentStatus.PAST_DUE, categoryBadge = "Presentations"),
            Assignment(id = "asg_02", subjectId = "sub_02", subjectName = "Biology", title = "Lab Report: Microscopy Specimen", dueDateText = "May 21 · Yesterday", dueTimeText = "5:00 pm", status = AssignmentStatus.PAST_DUE, categoryBadge = "Lab Reports"),
            Assignment(id = "asg_03", subjectId = "sub_02", subjectName = "Biology", title = "Reading: Chapter 3 (Cells)", dueDateText = "May 22 · Today", dueTimeText = "6:00 pm", status = AssignmentStatus.TODAY, categoryBadge = "Reading Checks"),
            Assignment(id = "asg_04", subjectId = "sub_04", subjectName = "Intro to Programming", title = "Lab 2: Variables & Operators", dueDateText = "May 22 · Today", dueTimeText = "11:59 pm", status = AssignmentStatus.TODAY, categoryBadge = "Labs"),
            Assignment(id = "asg_05", subjectId = "sub_01", subjectName = "Algebra II", title = "Problem Set 6: Matrices", dueDateText = "May 26", dueTimeText = "2:00 pm", status = AssignmentStatus.UPCOMING, categoryBadge = "Homework")
        )
    )

    private val _exams = MutableStateFlow<List<ExamCountdown>>(
        listOf(
            ExamCountdown(id = "ex_01", subjectName = "Algebra II", examTitle = "Midterm II", examDate = "June 2", daysRemaining = 6),
            ExamCountdown(id = "ex_02", subjectName = "CS101", examTitle = "Data Structures Final", examDate = "June 10", daysRemaining = 14)
        )
    )

    override fun getSubjects(): Flow<List<Subject>> = _subjects

    override fun getSubjectById(id: String): Flow<Subject?> =
        _subjects.map { list -> list.find { it.id == id } }

    override fun getAssignments(): Flow<List<Assignment>> = _assignments

    override fun getUpcomingExams(): Flow<List<ExamCountdown>> = _exams

    override suspend fun recordAttendance(subjectId: String, attended: Boolean) {
        _subjects.value = _subjects.value.map {
            if (it.id == subjectId) {
                val newAttended = if (attended) it.attendedClasses + 1 else it.attendedClasses
                it.copy(attendedClasses = newAttended, totalClasses = it.totalClasses + 1)
            } else it
        }
    }

    override suspend fun addAssignment(assignment: Assignment) {
        _assignments.value = listOf(assignment) + _assignments.value
    }

    override suspend fun toggleAssignmentSubmission(id: String) {
        _assignments.value = _assignments.value.map {
            if (it.id == id) it.copy(isSubmitted = !it.isSubmitted) else it
        }
    }
}
