package com.lifeos.app

import com.lifeos.app.core.data.repository.InMemoryTaskRepository
import com.lifeos.app.core.model.LifeArea
import com.lifeos.app.core.model.Task
import com.lifeos.app.core.model.TaskPriority
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TaskRepositoryTest {

    private lateinit var repository: InMemoryTaskRepository

    @Before
    fun setup() {
        repository = InMemoryTaskRepository()
    }

    @Test
    fun testInitialTasksLoaded() = runBlocking {
        val tasks = repository.getTasks().first()
        assertTrue("Initial tasks should not be empty", tasks.isNotEmpty())
        assertEquals(5, tasks.size)
    }

    @Test
    fun testAddTaskIncreasesCount() = runBlocking {
        val initialCount = repository.getTasks().first().size
        val newTask = Task(
            id = "test_task_99",
            title = "Test Automation Task",
            description = "Verifying repository reactive update",
            priority = TaskPriority.HIGH,
            linkedArea = LifeArea.SKILLS
        )
        repository.addTask(newTask)

        val updatedTasks = repository.getTasks().first()
        assertEquals(initialCount + 1, updatedTasks.size)
        assertTrue(updatedTasks.any { it.id == "test_task_99" })
    }

    @Test
    fun testToggleTaskCompletion() = runBlocking {
        val initialTask = repository.getTasks().first().first()
        val initialStatus = initialTask.isCompleted

        repository.toggleTaskCompletion(initialTask.id)

        val updatedTask = repository.getTasks().first().first { it.id == initialTask.id }
        assertEquals(!initialStatus, updatedTask.isCompleted)
    }

    @Test
    fun testDeleteTask() = runBlocking {
        val initialTasks = repository.getTasks().first()
        val targetId = initialTasks.first().id

        repository.deleteTask(targetId)

        val updatedTasks = repository.getTasks().first()
        assertFalse(updatedTasks.any { it.id == targetId })
        assertEquals(initialTasks.size - 1, updatedTasks.size)
    }
}
