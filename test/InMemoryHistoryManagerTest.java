package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager inMemoryTaskManager = Managers.getDefault();

    @Test
    void shouldReturnTrueIfTaskGetById() {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.getTasksById(1);
        assertTrue(inMemoryTaskManager.getHistory().contains(taskOne));
    }

    @Test
    void shouldReturnTrueIfTaskGetByIdAndRemoveTask() {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.getTasksById(1);
        inMemoryTaskManager.removeTaskById(1);
        assertTrue(inMemoryTaskManager.getHistory().contains(taskOne));
    }

    @Test
    void shouldReturnTrueIfTaskUpdateShowOldVersion() {
        Task taskBefore = new Task("1", "1");
        inMemoryTaskManager.createTask(taskBefore);
        inMemoryTaskManager.getTasksById(1);
        Task taskAfter = new Task("2", "2", 1);
        inMemoryTaskManager.updateTask(taskAfter);
        inMemoryTaskManager.getTasksById(1);
        assertTrue(inMemoryTaskManager.getHistory().contains(taskBefore));
        assertTrue(inMemoryTaskManager.getHistory().contains(taskAfter));
    }

    @Test
    void shouldReturnTrueIfTaskCheckAgain() {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.getTasksById(1);
        inMemoryTaskManager.getTasksById(1);
        assertEquals(1, inMemoryTaskManager.getHistory().size());
    }
}



