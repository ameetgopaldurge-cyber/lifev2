package com.lifeos.app

import com.lifeos.app.core.data.repository.InMemoryCollegeRepository
import com.lifeos.app.core.model.Subject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CollegeCalculationsTest {

    private lateinit var repository: InMemoryCollegeRepository

    @Before
    fun setup() {
        repository = InMemoryCollegeRepository()
    }

    @Test
    fun testAttendancePercentageCalculation() {
        val subject = Subject(
            id = "sub_math",
            name = "Calculus III",
            code = "MATH301",
            professor = "Dr. Euler",
            attendedClasses = 24,
            totalClasses = 30
        )
        // 24 / 30 = 80.0%
        assertEquals(80.0, subject.attendancePercentage, 0.001)
        assertFalse("80% should not be below 75% threshold", subject.isBelowThreshold)
    }

    @Test
    fun testAttendanceWarningThreshold() {
        val subject = Subject(
            id = "sub_chem",
            name = "Organic Chemistry",
            code = "CHEM201",
            professor = "Dr. Curie",
            attendedClasses = 14,
            totalClasses = 20 // 70.0%
        )
        assertEquals(70.0, subject.attendancePercentage, 0.001)
        assertTrue("70% should trigger attendance risk threshold", subject.isBelowThreshold)
    }

    @Test
    fun testRecordAttendanceIncrementsCounts() = runBlocking {
        val initial = repository.getSubjects().first().first { it.id == "sub_01" }
        val initAttended = initial.attendedClasses
        val initTotal = initial.totalClasses

        repository.recordAttendance("sub_01", attended = true)

        val updated = repository.getSubjects().first().first { it.id == "sub_01" }
        assertEquals(initAttended + 1, updated.attendedClasses)
        assertEquals(initTotal + 1, updated.totalClasses)
    }
}
